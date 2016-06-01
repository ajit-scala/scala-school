#!/usr/bin/env bash

trap 'error ${LINENO} $?' ERR

function error
{
    echo "smoke test script failed at line $1: $2"
    exit 1
}

echo "Wait for application startup: $(date) http://localhost:9000/metrics/master/json"
smokeTestMaster=$(wget --spider -S http://localhost:9000/metrics/master/json/ --tries=30 --waitretry=10 --retry-connrefused 2>&1 | grep "HTTP/" | awk '{print $2}')
if [ ! "$smokeTestMaster" == 200 ]; then
    msg1="smoke test master failed returned status code is $smokeTestMaster"
    error $(($LINENO - 2))  "$msg1"
fi
echo "Successfully started master at $(date)"


echo "smoke test done at $(date)"