#!/bin/bash
# PURPOSE: "import" new SpringBoot archives
echo "quick and dirty - undestand this file and then modify as needed"
exit 1

# download zip at https://start.spring.io/
unzipdir="api-gate-new"
sourcepath="${unzipdir}/api-gate"
set -x
test -e api-gate.zip || \
mv ~/Downloads/api-gate.zip . && unzip api-gate -d api-gate-new && rm api-gate.zip

diff "${sourcepath}"/build.gradle api-gate/
diff "${sourcepath}"/settings.gradle api-gate/

if [[ "${1:-}" == "cp" ]]; then
  cp "${sourcepath}"/build.gradle api-gate/
  cp "${sourcepath}"/settings.gradle api-gate/
fi


