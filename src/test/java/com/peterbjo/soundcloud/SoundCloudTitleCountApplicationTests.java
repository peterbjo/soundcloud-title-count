package com.peterbjo.soundcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@AutoConfigureMockMvc
@Import(TestConfiguration.class)
public class SoundCloudTitleCountApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FetchSongsTask fetchSongsTask;

    @TestConfiguration
    static class SoundCloudTestContextConfiguration {
        @Value("classpath:sound-cloud.json")
        Resource resourceFile;


        @Bean
        public RestTemplate restTemplate() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            FetchSongsTask.Song[] songs = mapper.readValue(resourceFile.getInputStream(), FetchSongsTask.Song[].class);
            RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
            when(restTemplate.getForObject(anyString(), any())).thenReturn(songs);
            return restTemplate;
        }
    }
    @Test
    public void endToEnd() throws Exception {
        fetchSongsTask.fetchAndPopulate();
        mvc.perform(MockMvcRequestBuilders.get("/words/?word=I&from=20190612&to=20190612")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word", is("i")))
                .andExpect(jsonPath("$.timeSeries[0].date", is("20190612")))
                .andExpect(jsonPath("$.timeSeries[0].count", is(3)));
    }
}
