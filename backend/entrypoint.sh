#!/bin/bash

start_dev_server() {
  java -jar -Dspring.profiles.active=dev $JAR_FILE_PATH
}

start_prod_server() {
  java -jar -Dspring.profiles.active=prod $JAR_FILE_PATH
}

start_server() {
  echo "Starting server in $STAGE mode"
  if [ "$STAGE" = "development" ]; then
    start_dev_server
  elif [ "$STAGE" = "production" ]; then
    start_prod_server
  else
    echo "Invalid stage: $STAGE"
    exit 1
  fi
}

start_server
