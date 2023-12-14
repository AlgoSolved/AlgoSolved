#!/bin/bash

current_timestamp=$(date +%s)
dump_command="pg_dump -c -U postgres -d algosolved"
container_id=$(docker ps | grep postgres | awk '{print $1}')
docker exec -t ${container_id} $dump_command > db_dump_files/db_dump_${current_timestamp}.sql
