#!/usr/bin/env bash

# Don't execute this, these are the commands that have been executed in the tutorial, just as resource.
# To run the application use Docker compose.
docker run -d \
    --name demo-mysql \
    -e MYSQL_ROOT_PASSWORD=p4SSW0rd \
    -e MYSQL_DATABASE=demo \
    -e MYSQL_USER=dbuser \
    -e MYSQL_PASSWORD=dbp4ss \
    mysql:latest
docker run -d \
    --name spring-boot-jpa-docker-webapp \
    --link demo-mysql:mysql \
    -p 8080:8080 \
    g00glen00b/spring-boot-jpa-docker-webapp
