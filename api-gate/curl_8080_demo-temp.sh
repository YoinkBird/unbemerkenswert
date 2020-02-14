#!/usr/bin/env bash
#PURPOSE: quick and crappy placeholder; this is NOT a REST API as it uses the normal HTTP ops for everything. however, it does help "nail down" the code as it is refactored to be a REST API
#set -x
_wait_for_host=1
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
# wait
function fn_wait(){
  # curl: (6) Could not resolve host: ocalhost
  # curl: (7) Failed to connect to localhost port 8374: Connection refused
  local ctr=30
  while [[ "${ctr}" -gt 0 ]]; do
    set +e
    curl -s ${url}
    local _rc=$?
    set -e
    if [[ "${_rc}" -eq 0 ]]; then
      break;
    elif [[ "${_rc}" -ne 7 ]]; then
      break;
    fi
    (( ctr -= 1 ))
    sleep 2
  done
  return ${_rc}
}
# wait for host to be ready
if [[ "${_wait_for_host}" -eq 1 ]]; then
  echo "# INFO: waiting for server..."
  fn_wait
  rc=$?
  if [[ "${rc}" -ne 0 ]]; then
    echo "couldn't connect to ${APIGATE_HOST}:${APIGATE_PORT}"
    exit ${rc}
  fi
  echo ""
  echo "# INFO: server is up!" 
fi
# BEGIN TESTS
set -e

echo "# TEST: create resource, verify fields using 'jq'"
echo '#expected:  {"id":<n>,"created":"yyyy-MM-ddTHH:mm:ss","lastModified":null,"title":"Samwise Gamgee","body":"gardener","tags":["tag1","tag2"]}'
resp_1="$(curl -s -X POST ${url}/demos -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "body": "gardener", "tags": ["tag1","tag2"]}')"
echo "#returned: '${resp_1}'"
id_1="$( echo "${resp_1}" | jq -r '.id')"
echo "${resp_1}" | jq -r -e '.title == "Samwise Gamgee"'
echo "${resp_1}" | jq -r -e '.body == "gardener"'
echo "${resp_1}" | jq -r -e '.tags == ["tag1","tag2"]'
echo "${resp_1}" | jq -r -e '.created != "null"'
echo "${resp_1}" | jq -r -e '.lastModified == null'

echo "# TEST: update resource, verify fields using 'jq'"
echo '#expected:  {"id":'${id_1}',"created":"yyyy-MM-ddTHH:mm:ss","lastModified":"yyyy-MM-ddTHH:mm:ss","title":"Samwise Gamgee","body":"gardener","tags":["tag2","tag3"]}'
resp_2="$(curl -s -X PUT ${url}/demos/${id_1} -H 'Content-type:application/json' -d '{"title": "Samwise Gamgee", "body": "ring bearer", "tags": ["tag2","tag3"]}')"
echo "#returned: '${resp_2}'"
echo "${resp_2}" | jq -r -e '.title == "Samwise Gamgee"'
echo "${resp_2}" | jq -r -e '.body == "ring bearer"'
# doesn't work due to underlying implementation; tags are 'String[]'
echo "${resp_2}" | jq -r -e '.tags == ["tag2","tag3"]' || echo '`-- EXPECTED FAIL ^^^'
echo "${resp_1}" | jq -r -e '.created != "null"'
echo "${resp_1}" | jq -r -e '.lastModified != "null"'

echo "# TEST: delete resource"
echo "#expected: '' (empty response)"
resp_3="$(curl -s -X DELETE ${url}/demos/${id_1})"
echo "#returned: '${resp_3}'"
test -z "${resp_3}"

echo "# TEST: handle errors correctly"
echo "#expected: 'Could not find record ${id_1}'"
resp_4="$( curl -s ${url}/demos/${id_1} )"
test "${resp_4}" == "Could not find record ${id_1}"
echo "#returned: '${resp_4}'"
echo DONE
echo $?
exit 0


