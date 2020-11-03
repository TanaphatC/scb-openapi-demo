FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/scb-openapi-demo-1.0.war
COPY ${JAR_FILE} scb-openapi-demo-1.0.war
ENTRYPOINT ["java","-jar","/scb-openapi-demo-1.0.war"]