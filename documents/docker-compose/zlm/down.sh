#!/bin/bash
cd /d/custom/docker/zlm/quzheng
docker-compose -f 'docker-compose-quzheng.yml' down
sleep 5

cd /d/custom/docker/zlm/wvp
docker-compose -f 'docker-compose-wvp.yml' down
sleep 5

cd /d/custom/docker/zlm/zlmediakit
docker-compose -f 'docker-compose-zlmediakit.yml' down