FROM pw_env
WORKDIR /data/devservice
COPY . .
RUN mvn clean package
CMD ["java", "-jar", "runner/target/development-runner-jar-with-dependencies.jar"]