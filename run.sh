#!/bin/bash
set -x
set -eu
arg="${1:-}"
if [[ -z "${arg}" ]]; then
  docker-compose up --abort-on-container-exit
  rc=$?
elif [[ "${arg}" == "test" ]]; then
  docker-compose run --rm --no-deps --service-ports --entrypoint bash test
fi

echo $rc
