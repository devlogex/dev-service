#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Projects/KeyAWS/Key081020.pem runner/target/development-runner-jar-with-dependencies.jar  ubuntu@13.212.152.111:/home/ubuntu/workspace
