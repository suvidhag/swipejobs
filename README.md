# Technical Assignment for Swipejobs
Matching engine that presents jobs with appropriate worker
# Getting Started
Run below command from project directory. 

mvn clean install
java -jar target\job-matching-server-0.0.1-SNAPSHOT.jar

# Integration with external APIs
This service currently depends on below APIs to get the list of available Workers and Jobs
Workers API : http://test.swipejobs.com/api/workers
Jobs API    : http://test.swipejobs.com/api/jobs

# Published API 
### GET /matches/{workerId}
This REST API matches that will take a workerId and return no more than configurable number of appropriate jobs for that Worker.Number of Maximum jobs result can be configured using maxJobSearchResults configuration variable

Example :  maxJobSearchResults:3 // will limit the matching result to 3
           maxJobSearchResults:1 // will limit the matching result to 1

The application can also be configured to match the jobs based upon some specific conditions that are applied in the order specified. As of now the application supports following set of conditions {LICENSE, REQD_CERTIFICATES, DISTANCE, SKILLS}
Example 1) matching.conditions: LICENSE, REQD_CERTIFICATES, DISTANCE
        2) matching.conditions: LICENSE, REQD_CERTIFICATES
        3) matching.conditions: DISTANCE
        4) matching.conditions: LICENSE, REQD_CERTIFICATES, DISTANCE, SKILLS

# Unit Testing
mvn test
