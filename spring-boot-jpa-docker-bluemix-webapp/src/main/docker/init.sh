#!/bin/bash

while ! exec 6<>/dev/tcp/${MYSQL_PORT_3306_TCP_ADDR}/${MYSQL_PORT_3306_TCP_PORT}; do
    echo "Trying to connect to MySQL at ${MYSQL_PORT}..."
    sleep 10
done

java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -Xms128m -Xmx256m -jar /app.jar
