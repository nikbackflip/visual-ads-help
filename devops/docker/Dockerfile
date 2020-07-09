FROM openjdk:15-jdk-alpine
RUN mkdir /storage
COPY VADSH-*.war /VADSH.war
CMD ["/bin/sh", "-c", "java -jar -Dspring.profiles.active=docker /VADSH.war"]

