FROM openjdk:15-jdk-alpine
COPY target/VADSH-*.war /VADSH.war
CMD ["java", "-jar", "/app.war"]

