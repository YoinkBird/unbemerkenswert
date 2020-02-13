#!/usr/bin/env bash
set -x
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
url="${url}/demo"
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
  set +x
  fn_wait
  rc=$?
  set -x
  if [[ "${rc}" -ne 0 ]]; then
    echo "couldn't connect to ${APIGATE_HOST}:${APIGATE_PORT}"
    exit ${rc}
  fi
fi
# delete all
function fn_delete_all(){
  links=($( curl -s "${url}" | jq -r '._embedded.demo[]._links.self.href' ))
  echo "${links[@]}"
  for link in "${links[@]}"; do
    # TODO: http response
    resp="$(curl -s -X DELETE "${link}" )"
  done
  postlinks=($( curl -s "${url}" | jq -r '._embedded.demo[]._links.self.href' ))
  if [[ "${#postlinks[@]}" -ne 0 ]]; then
    echo "ERROR: delete failed, res still on server. ${postlinks[@]}"
    return 1;
  fi
}

# curl -i -H "Content-Type:application/json" -d '{"firstName": "Frodo", "lastName": "Baggins"}' localhost:8080/people
# curl -i localhost:8080/people/search/findByLastName?name=Baggins
curl ${url}
#curl -i -H "Content-Type:application/json" -d '{"firstName": "Frodo", "lastName": "Baggins"}' ${url}
self_href_1="$(curl -s -H ${curl_content_type} -d '{"content": "thing1"}' ${url} | jq -r '._links.self.href')"
echo $?
self_href_2="$(curl -s -H ${curl_content_type} -d '{"content": "thing2"}' ${url} | jq -r '._links.self.href')"
echo $?
echo $self_href_1
curl -s ${url}/search/findByContent

curl -s -X PUT -H ${curl_content_type} -d '{"content": "cat"}' ${self_href_1}


curl -s ${url}/search/findByContent?name=thing1
curl -s ${url}/search/findByContent?name=cat
curl ${url}

# delete this one thing
links=($( curl -s '${url}/search/findByContent?name=thing1' | jq -r '._embedded.demo[]._links.self.href' ))
echo "${links[@]}"
for link in "${links[@]}"; do
  # TODO: http response
  resp="$(curl -s -X DELETE "${link}" )"
done
postlinks=($( curl -s '${url}/search/findByContent?name=thing1' | jq -r '._embedded.demo[]._links.self.href' ))
if [[ "${#postlinks[@]}" -ne 0 ]]; then
  echo "ERROR: delete failed, res still on server. ${postlinks[@]}"
  exit 1;
fi
curl -s ${url}/search/findByContent?name=thing1
curl -s ${url}

fn_delete_all
rc="$?"

${cmd_show_all}
echo "FINISH"
exit 0
