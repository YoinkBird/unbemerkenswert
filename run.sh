#!/bin/bash
set -x
set -eu
docker-compose up --abort-on-container-exit
echo $?
