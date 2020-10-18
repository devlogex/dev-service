#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Downloads/Key081020.pem runner/target/development-runner-jar-with-dependencies.jar  ubuntu@52.221.234.137:/home/ubuntu/workspace