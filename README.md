# SoundCloud Title Word Count
Uses a H2 relational in-memory database, with a web endpoint for queries.  
Once every day a job is scheduled to fetch new songs from [https://api.soundcloud.com/tracks?client_id=3a792e628dbaf8054e55f6e134ebe5fa]

## Run locally
```bash
$ mvn clean install
$ java -jar target/soundcloud-title-count-0.0.1-SNAPSHOT.jar
```
## Usage
Query the service with parameters like:
```bash
curl "http://localhost:8080/words/?word=love&from=20190617&to=20190618"
```
* <b>word</b>: is the word to search for  
* Only word counts added between <b>from</b> and <b>to</b> are provided in the result

## Result
Result is returned as JSON:
```
{"word":"to","timeSeries":[{"date":"20190617","count":2}]}
```
## Database
The application uses a H2 in memory database.  
To query the database open [http://localhost:8080/h2-console].  
* jdbc-url: jdbc:h2:mem:testdb  
* Username: sa  
* Password: leave blank