#!/bin/bash

start_dev_server() {
  (sleep 30; ./gradlew buildAndReload --continuous -PskipDownload=true -x Test) &
  ./gradlew bootRun -PskipDownload=true -Dspring.profiles.active=dev -Dfile.encoding=UTF-8


}

start_prod_server() {
  ./gradlew bootRun -Dspring.profiles.active=dev -Dfile.encoding=UTF-8
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
