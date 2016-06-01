#!/usr/bin/env bash

while [[ $# -gt 0 ]]; do
    opt="$1"
    shift;
    case "$opt" in
        "--account_subdomain"               )  account_subdomain="$1";                   shift;;
    esac
done

trap 'error ${LINENO} $?' ERR

function error
{
    echo "smoke test script failed at line $1: $2"
    exit 1
}

echo "account_subdomain = $account_subdomain"

echo "kafka smoke test started at $(date)"
declare -a expectedKafkaTopic=("cats-enriched-listings" "raw-listings")
listTopic="/kafka_2.11-0.9.0.1/bin/kafka-topics.sh --zookeeper=zookeeper.$account_subdomain.autoscout24.com:2181 --list"
existingKafkaTopic=$( $listTopic | tr " " "\n")

COUNTER=0
for i in ${existingKafkaTopic[@]}; do
        for j in ${expectedKafkaTopic[@]}; do
                if [ "$i" == "$j" ]; then
                        COUNTER=$((COUNTER+1))
                        echo "kafka topis $j exist in environment"
                fi
        done
done

if [ $COUNTER -eq ${#expectedKafkaTopic[@]} ]
then
        echo "kafka smoke test PASSED successfully!!!"
else
        msg1="kafka smoke test FAILED because the expected kakfa topics <<${expectedKafkaTopic[*]}>> not exist!!"
        error $(($LINENO - 2))  "$msg1"
fi
echo "kafka smoke test done at $(date)"