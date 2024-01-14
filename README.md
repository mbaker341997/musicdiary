# Running
To run application and/or the tests, you'll need to start the postgres containers.
Make sure you have Docker installed from the root directory, run
```shell
docker-compose up
```

The main database will be on port 5432, the test database will be on 5433. 

Adminer as a simple database integration client is running on http://localhost:8081
Log in with the credentials found in `docker-compose.yml`

With the databases running, you can run the actual application. I just press the buttons in 
IntelliJ, with JDK11 set as the project SDK. 

# Swagger 
This application runs with swagger. View at http://localhost:8080/swagger-ui/index.html