#!/bin/bash

set -exuo pipefail

SERVICE_NAME=carhistory-classified-import
bucket_region=${AWS_REGION:-${AWS_DEFAULT_REGION:-eu-west-1}}
ARTEFACTS_BUCKET=as24-artifacts-$bucket_region

ME=`basename $0`
OS=`uname`
if [ "$OS" = "Darwin" ] ; then
    MYFULL="$0"
else
    MYFULL=`readlink -sm $0`
fi
MYDIR=`dirname ${MYFULL}`

fail()
{
  echo "[$ME] FAIL: $*"
  exit 1
}

[ -d "${MYDIR}/target" ] || fail "Does not look like a build has happened here. Directory ${MYDIR}/target doesn't exist"

SERVICE_ARTEFACT="${MYDIR}/target/scala-2.11/${SERVICE_NAME}-assembly-${GO_PIPELINE_LABEL}.jar"
[ -f "${SERVICE_ARTEFACT}" ] || fail "Artefact doesn't exist: ${SERVICE_ARTEFACT}"


[ -d "${MYDIR}/target/scala-2.11" ] || mkdir ${MYDIR}/target/scala-2.11
SPARK_CONFIG_ARTEFACT="${MYDIR}/target/scala-2.11/${SERVICE_NAME}-spark-conf-${GO_PIPELINE_LABEL}.tgz"
tar -czvf ${MYDIR}/target/scala-2.11/${SERVICE_NAME}-spark-conf-${GO_PIPELINE_LABEL}.tgz -C deploy/spark-conf .
[ -f "${SPARK_CONFIG_ARTEFACT}" ] || fail "Artefact doesn't exist: ${SPARK_CONFIG_ARTEFACT}"

cp "${MYDIR}/deploy/bin/spark_smoketest.sh" "${MYDIR}/target/scala-2.11"
cp "${MYDIR}/deploy/metrics/spark_metrics.rb" "${MYDIR}/target/scala-2.11"

cp "${MYDIR}/deploy/bin/kafka_smoketest.sh" "${MYDIR}/target/scala-2.11"

tar -czvf ${MYDIR}/${SERVICE_NAME}-${GO_PIPELINE_LABEL}.tgz -C ./target/scala-2.11 "${SERVICE_NAME}-assembly-${GO_PIPELINE_LABEL}.jar" "${SERVICE_NAME}-spark-conf-${GO_PIPELINE_LABEL}.tgz" spark_smoketest.sh spark_metrics.rb kafka_smoketest.sh

echo "[$ME] Uploading artefacts to S3"
aws s3 cp ${MYDIR}/${SERVICE_NAME}-${GO_PIPELINE_LABEL}.tgz "s3://${ARTEFACTS_BUCKET}/${SERVICE_NAME}/"