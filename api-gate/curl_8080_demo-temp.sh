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
set -o pipefail
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

set -u
note_1_title="java development"
# NOTE:just a placeholder. in bash and inline-json cannot pass these spaced-out strings due to the way bash flattens the list. bash considered harmfull :-)
note_1_body_A="rest api"
note_1_body_B="springboot framework works well"
note_2="containers"
echo "# TEST: create resource, verify fields using 'jq'"
echo '#expected:  {"id":<n>,"created":"yyyy-MM-ddTHH:mm:ss","lastModified":null,"title":"'${note_1_title}'","body":"rest api","tags":["tag1","tag2"]}'
json_1="$( printf '{"title": "%s", "body": "%s", "tags": ["tag1","tag2"]}' "${note_1_title[@]}" "${note_1_body_A[@]}")"
resp_1="$(curl -s -X POST ${url}/demos -H 'Content-type:application/json' -d "${json_1[@]}")"
echo $resp_1
echo "#returned: '${resp_1}'"
id_1="$( echo "${resp_1}" | jq -r '.id')"
# hard-coded oracles based on the note_1_<etc> vars; don't want to have accidents with variables
echo "${resp_1}" | jq -r -e '.title == "java development"'
echo "${resp_1}" | jq -r -e '.body == "rest api"' 
echo "${resp_1}" | jq -r -e '.tags == ["tag1","tag2"]'
echo "${resp_1}" | jq -r -e '.created != null'
echo "${resp_1}" | jq -r -e '.lastModified != null'
# this is for future comparison. since it is 'null', need to run as 'jq' instead of 'jq -e'
last_created_1="$(echo "${resp_1}" | jq -r '.created')"
last_mod_1="$(echo "${resp_1}" | jq -r '.lastModified')"
test "${last_created_1}" == "${last_mod_1}"
unset resp_1

echo "# TEST: update resource, verify fields using 'jq'"
echo '#expected:  {"id":'${id_1}',"created":"yyyy-MM-ddTHH:mm:ss","lastModified":"yyyy-MM-ddTHH:mm:ss","title":"'${note_1_title}'","body":"rest api","tags":["tag2","tag3"]}'
json_2="$( printf '{"title": "%s", "body": "%s", "tags": ["tag1","tag2"]}' "${note_1_title[@]}" "${note_1_body_B[@]}" )"
resp_2="$(curl -s -X PUT ${url}/demos/${id_1} -H 'Content-type:application/json' -d "${json_2[@]}")"
echo "#returned: '${resp_2}'"
# hard-coded oracles based on the note_1_<etc> vars; don't want to have accidents with variables
echo "${resp_2}" | jq -r -e '.title == "java development"'
echo "${resp_2}" | jq -r -e '.body == "springboot framework works well"' 
# doesn't work due to underlying implementation; tags are 'String[]'
echo "${resp_2}" | jq -r -e '.tags == ["tag2","tag3"]' || echo '`-- EXPECTED FAIL ^^^'
echo "${resp_2}" | jq -r -e '.created != null'
echo "${resp_2}" | jq -r -e '.lastModified != null'
# compare timestamp
last_mod_2="$(echo "${resp_2}" | jq -r -e '.lastModified')"
test "${last_mod_2}" != "${last_mod_1}"
unset resp_2

echo "# TEST: update resource, check lastModified timestamp. verify fields using 'jq'"
echo '#expected:  {"id":'${id_1}',"created":"yyyy-MM-ddTHH:mm:ss","lastModified":"yyyy-MM-ddTHH:mm:ss","title":"'${note_1_title}'","body":"rest api","tags":["tag2","tag3"]}'
# re-use json_2 from previous test
resp_2b="$(curl -s -X PUT ${url}/demos/${id_1} -H 'Content-type:application/json' -d "${json_2[@]}")"
echo "#returned: '${resp_2b}'"
# hard-coded oracles based on the note_1_<etc> vars; don't want to have accidents with variables
echo "${resp_2b}" | jq -r -e '.title == "java development"'
echo "${resp_2b}" | jq -r -e '.body == "springboot framework works well"'
# doesn't work due to underlying implementation; tags are 'String[]'
echo "${resp_2b}" | jq -r -e '.tags == ["tag2","tag3"]' || echo '`-- EXPECTED FAIL ^^^'
echo "${resp_2b}" | jq -r -e '.created != "null"'
echo "${resp_2b}" | jq -r -e '.lastModified != "null"'
# compare timestamp
last_mod_2b="$(echo "${resp_2b}" | jq -r -e '.lastModified')"
test "${last_mod_2b}" != "${last_mod_2}"
unset resp_2b

echo "# TEST: delete resource"
echo "#expected: '' (empty response)"
resp_3="$(curl -s -X DELETE ${url}/demos/${id_1})"
echo "#returned: '${resp_3}'"
test -z "${resp_3}"
unset resp_3

echo "# TEST: handle errors correctly"
echo "#expected: 'Could not find record ${id_1}'"
resp_4="$( curl -s ${url}/demos/${id_1} )"
test "${resp_4}" == "Could not find record ${id_1}"
echo "#returned: '${resp_4}'"
unset resp_4
echo DONE
echo $?
exit 0


