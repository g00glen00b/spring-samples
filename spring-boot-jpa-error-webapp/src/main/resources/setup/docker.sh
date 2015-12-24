#!/usr/bin/env bash
docker-machine create --driver virtualbox default
eval "$(docker-machine env default)"
docker run --name demo-mysql -e MYSQL_ROOT_PASSWORD=p4SSW0rd -p 3306:3306 -d mysql:latest