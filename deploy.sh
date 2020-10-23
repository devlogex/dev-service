#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Public/Key201020.pem runner/target/development-runner-jar-with-dependencies.jar  ubuntu@13.250.33.156:/home/ubuntu/workspace