#!/usr/bin/env bash

docker-compose down &&

./seed-spring/gradlew assemble &&
docker build seed-spring -t seed-spring &&

npm install --prefix seed-vue &&
npm run --prefix seed-vue build &&

docker-compose up --renew-anon-volumes --build --force-recreate -d  &&
docker ps | grep seed
