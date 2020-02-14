#!/bin/bash
set -x
set -eu
arg="${1:-}"
if [[ -z "${arg}" ]]; then
  # too cumbersome: # docker-compose up --abort-on-container-exit
  echo "compile java and run bash e2e test"
  docker-compose up --build
  rc=$?
elif [[ "${arg}" == "test" ]]; then
  docker-compose run --rm --no-deps --service-ports --entrypoint bash test
fi

echo $rc
