#!/usr/bin/env bash
#PURPOSE: quick and crappy placeholder
set -x
_wait_for_host=0
host="localhost";
port=8080
if [[ ! -z "${APIGATE_HOST:-}" ]]; then
  host="${APIGATE_HOST}"
fi
if [[ ! -z "${APIGATE_PORT:-}" ]]; then
  port="${APIGATE_PORT}"
fi
url="${host}:${port}"
url="${url}/demo"
curl_content_type="Content-Type:application/json"
cmd_show_all="curl -s ${url}"

set -e
curl -X POST localhost:8080/demos -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
curl -X PUT localhost:8080/demos/1 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
curl -X DELETE localhost:8080/demos/1
curl localhost:8080/demos/1
#expected: Could not find employee 3
