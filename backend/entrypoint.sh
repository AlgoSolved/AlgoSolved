#!/bin/bash

start_dev_server() {
  (sleep 30; ./gradlew buildAndReload --continuous -PskipDownload=true -x Test) &
  ./gradlew bootRun -PskipDownload=true -Dspring.profiles.active=dev
}

start_prod_server() {
  java -jar -Dspring.profiles.active=prod $JAR_FILE_NAME
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
