#!/bin/bash

docker-compose down &&

./gradlew -p seed-spring clean build &&
docker build seed-spring -t seed-spring &&

npm install --prefix seed-vue &&
npm run --prefix seed-vue build &&

docker-compose up --renew-anon-volumes --build --force-recreate -d  &&
docker ps | grep seed
