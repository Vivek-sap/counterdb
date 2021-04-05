#!/bin/bash

set -eu

es_host="http://localhost:9200"

function usage {
  echo "Fetch entries of WCD history."
  echo " "
  echo "Options:"
  echo "-f <VALUE> field to search in"
  echo "-v <VALUE> value of the field"
  echo "-d         use coma-dev Elasticsearch instance"
  echo "-p <VALUE> do search like pollux for "
  echo " "
  echo "-h         Help."
}

while getopts "f:v:dhp:" opt; do
  case "$opt" in
    f) field=$OPTARG;;
    v) value=$OPTARG;;
    d) es_host="https://elasticsearch.internal.dev.coma-vf.de";;
    p) pollux_search=$OPTARG;;
    h|*) usage; exit 2;;
  esac
done
shift "$((OPTIND - 1))"

[ $# != 0 ] && { usage; exit 1;}


if [[ -z ${field-} || -z ${value-} ]]; then
query=$(cat <<EOF
{
  "query": {
    "match_all": {
    }
  }
}
EOF
)
else
query=$(cat <<EOF
{
  "query": {
    "bool": {
      "must": [
        { "term": { "${field}.keyword": { "value": "${value}" } } }
      ]
    }
  }
}
EOF
)
fi

if [[ ! -z ${pollux_search-} ]]; then
value=$pollux_search
query=$(cat <<EOF
{
  "sort" : [
    { "invocationId" : {"order" : "desc"}},
    {  "modifiedTime" : {"order" : "desc"}},
    "_score"],
    "query": {
      "bool": {
        "should": [
          { "match": { "lineId.keyword": "${value}" } },
          { "match": { "metadata.coma.wholesaleCustomer.lineId.keyword": "${value}" } },
          { "match": { "metadata.coma.wholesaleCustomer.internalCustomerId.keyword": "${value}" } },
          { "match": { "metadata.coma.wholesaleCustomer.order.internalOrderId.keyword": "${value}" } },
          { "match": { "externalOrderId.keyword": "${value}" } },
          { "match": { "metadata.coma.wholesaleCustomer.order.externalOrderId.keyword": "${value}" } },
          { "match": { "status": "${value}" } },
          { "match": { "metadata.coma.wholesaleCustomer.device.mac.keyword": "${value}" } },
          { "match": 
            { "invocationId": {"query" : "${value}", "lenient": true} } 
          }
        ]
      }
    }
}
EOF
)
fi

curl -X GET "${es_host}/wcd-history*/_search" -H "Content-Type: application/json" -d "${query}"
