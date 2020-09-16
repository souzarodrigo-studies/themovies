#!/bin/sh
if [ $(docker ps -a -f name=themovies | grep -w themovies | wc -l) -eq 1 ]; then
  docker rm -f themovies
fi
mvn clean package && docker build -t themovies/themovies .
docker run -d -p 9080:9080 -p 9443:9443 --name themovies themovies/themovies
