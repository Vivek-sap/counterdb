#!/bin/bash
set -eu

echo "Calling wcd with $1"

curl -H "Accept: application/xml" \
     -H "Content-Type: application/xml" \
     -H "SOAPAction: StartInvocationChain" \
     -X POST http://localhost:8080/services/wcd \
     -d @"${1}"

