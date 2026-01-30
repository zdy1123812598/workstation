#!/bin/bash
cd /d/custom/docker/zlm/zlmediakit
docker-compose -f 'docker-compose-zlmediakit.yml' up -d --build
sleep 10

cd /d/custom/docker/zlm/wvp
docker-compose -f 'docker-compose-wvp.yml' up -d --build
sleep 5

cd /d/custom/docker/zlm/quzheng
docker-compose -f 'docker-compose-quzheng.yml' up -d --build