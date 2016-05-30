#!/bin/bash

set -euo pipefail

ME=`basename $0`
OS=`uname`
if [ "$OS" = "Darwin" ] ; then
    MYFULL="$0"
else
    MYFULL=`readlink -sm $0`
fi
MYDIR=`dirname ${MYFULL}`
echo MYDIR=${MYDIR}

echo "[${ME}] Preparing Backend application"
echo "[${ME}] Building backend..."
exec "${MYDIR}/activator" clean compile test it:test assembly -Dsbt.log.noformat=true
