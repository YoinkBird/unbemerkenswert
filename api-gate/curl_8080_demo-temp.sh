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
#url="${url}/demo"
curl_content_type="Content-Type:application/json"
cmd_show_all="curl -s ${url}"

set -e
echo "CAVEAT - can only run once, everything is hard-coded!"
echo #expected: {"id":1,"body":null}
resp="$(curl -s -X POST ${url}/demos -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "body": "gardener", "tags": ["tag1","tag2"]}')"
id_1="$( echo "${resp}" | jq -r '.id')"
echo "#expected: {"id":1,"body":null}"
curl -X PUT ${url}/demos/${id_1} -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "body": "ring bearer", "tags": ["tag2","tag3"]}'
curl -X DELETE ${url}/demos/${id_1}
echo "#expected: Could not find employee ${id_1}"
curl ${url}/demos/${id_1}
echo DONE
echo $?
exit 0


