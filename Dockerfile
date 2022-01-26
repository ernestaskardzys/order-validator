FROM openjdk:11-jre-slim

COPY target/godtask.jar /

ENTRYPOINT ["java", "-jar", "godtask.jar"]
