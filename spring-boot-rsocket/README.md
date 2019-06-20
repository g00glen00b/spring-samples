# Example using the Spring boot starter for Spring Data R2DBC

## Setup

This project demonstrates how to use R2DBC with Spring boot to reactively connect to a relational database. The database I'm using in this example is PostgreSQL. To set up a PostgreSQL database, you can run the Docker container by using the following command:

```
docker-compose up
```

Additionally, you can run the producer Spring boot application by using Maven with the **producer** profile:

```
mvn spring-boot:run -P producer
```

This application will create the schema if not present yet, and insert a few records. Additionally it will set up an RSocket server on port 8000.

To run the consumer application, you can use Maven with the **consumer** profile:

```
mvn spring-boot:run -P consumer
```


