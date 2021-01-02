#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Public/Key201020.pem runner/target/development-runner-jar-with-dependencies.jar  ubuntu@13.212.105.184:/home/ubuntu/workspace
