# flyte
flyte is a Spring Boot (Java backend web framework) webapp used for finding flights. There is no frontend associated with flyte, and static files are served up using a templating engine called [Thymeleaf](https://www.thymeleaf.org/).

# Dependencies
All dependencies are included in the Maven [pom.xml](https://github.com/brilam/flyte/blob/master/pom.xml). Though, if you are interested in seeing what the dependencies are:
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- mariadb-java-client
- spring-boot-starter-thymeleaf
- bcrypt

# Getting Started
1) Build the JAR by doing:
```
mvn clean package
```

2) Run the JAR by doing:
```
cd target
java -jar flyte-0.0.1-SNAPSHOT.jar
```

3) Add airlines, flight information, locations, etc to the database.

4) The webapp is ready on localhost:8080.
