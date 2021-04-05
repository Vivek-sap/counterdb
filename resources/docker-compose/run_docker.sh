#!/bin/bash

set -eu

declare -A docker_compose_files=(["activemq"]="docker-compose-activemq.yml" ["elasticsearch"]="docker-compose-elasticsearch.yml" ["mysql"]="docker-compose-mysql.yml")

function usage {
  echo "Start docker compose."
  echo " "
  echo "Options:"
  echo "-m  Do not start mysql"
  echo "-e  Do not start elasticsearch"
  echo "-a  Do not start activemq"
  echo " "
  echo "-h  Help."
}

while getopts "meah" opt; do
  case "$opt" in
    m) echo "Ignoring MySQL"; unset docker_compose_files["mysql"];;
    e) echo "Ignoring Elasticsearch"; unset docker_compose_files["elasticsearch"];;
    a) echo "Ignoring ActivqMQ"; unset docker_compose_files["activemq"];;
    h|*) usage; exit 2;;
  esac
done
shift "$((OPTIND - 1))"

[ $# != 0 ] && { usage; exit 1;}

current_directory="$( dirname "${BASH_SOURCE[0]}" )"

params=""
for file in "${docker_compose_files[@]}"; do
  params="-f ${current_directory}/${file} ${params}"
done

docker-compose $params up
