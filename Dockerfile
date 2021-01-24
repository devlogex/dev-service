FROM pw_env
WORKDIR /data/devservice
COPY . .
RUN mvn -B -f /data/devservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "runner/target/development-runner-jar-with-dependencies.jar"]