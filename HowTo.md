### This How to helps to qucklz start with verify ###

 1. Build the project mvn clean package or make build

 2. Start Ingestion service inside the module
docker-compose -f infra/docker-compose.yml up -d

 3. Create topics for jobs could be done with UI
    localhost:8080 availabe Kafka Cluster
    - raw-user-events
    - enrieched-user-events
    - user-profile-updates

 4. Run Rest API
  cd ingestion-service && mvn spring-boot:run
  UI Rest available http://localhost:9090/swagger-ui/index.htm

 5. Run event enrichment job with 
  - localhost:8081 flink cluster is available
  add with UI streaming-event-enricher-1.0-SNAPSHOT.jar
  class com.example.jobs.EventEnrichJob

 6. Run profile aggreator job
 the same way as above Flink add streaming-profile-aggregator-1.0-SNAPSHOT.jar
 class com.example.jobs.ProfileWriterJob

 7. Run profile store job
 add to Flink cluster the streaming-profile-store-1.0-SNAPSHOT.jar


##### How To view results of working apps #####
 - Most Important to pass with Swagger "userId": some string value by this id would be calculated and store in Redis.
 items - collections in the list
 For simplify process category could be TECH BOOKS SPORT
 this would be grouping and calculate user preferences
 eventType - could be also in VIEW, CLICK, ADD_TO_CHART, PURCHASE