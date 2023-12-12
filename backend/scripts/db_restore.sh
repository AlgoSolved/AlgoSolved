#!/bin/bash

dumpfile=$1
if [ -z $dumpfile ]; then
    dumpfile=$(ls -t db_dump_files/db_dump_*.sql | head -n 1)
fi

restore_command="psql -U postgres -d algosolved"
container_id=$(docker ps | grep postgres | awk '{print $1}')

cat ${dumpfile} | docker exec -i ${container_id} ${restore_command}
