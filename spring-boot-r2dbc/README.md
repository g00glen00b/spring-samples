# Example using the Spring boot starter for Spring Data R2DBC

## Setup

This project demonstrates how to use R2DBC with Spring boot to reactively connect to a relational database. The database I'm using in this example is PostgreSQL. To set up a PostgreSQL database, you can run the Docker container by using the following command:

```
docker-compose up
```

Additionally, you can run the Spring boot application by using Maven:

```
mvn spring-boot:run
```

This application will create the schema if not present yet, and insert a few records. Accessing the data can be done through a REST API by calling http://localhost:8080/api/person.
