# Example using the Spring boot starter for RSocket with R2DBC

## Setup

This project demonstrates how RSocket can be used with Spring boot to stream our data reactively over the network. Additionally we're using R2DBC to reactively fetch data from a relational database.
The database used in the project is PostgreSQL. To set up PostgreSQL, you can run a Docker container by using Docker Compose:

```
docker-compose up
```

The application itself contains two smaller applications, one being used to fetch data from the database provide an RSocket server, and another to consume the messages from RSocket.

To run the producer application, you can use Maven with the **producer** profile:

```
mvn spring-boot:run -P producer
```

This application will create the schema if not present yet, and insert a few records. Additionally it will set up an RSocket server on port 8000.

To run the consumer application, you can use Maven with the **consumer** profile:

```
mvn spring-boot:run -P consumer
```


