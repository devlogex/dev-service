#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Public/Key081020.pem runner/target/development-runner-jar-with-dependencies.jar  ubuntu@13.229.44.187:/home/ubuntu/workspace
