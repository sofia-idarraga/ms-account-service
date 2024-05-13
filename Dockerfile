FROM maven:3.8.4-openjdk-17 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY application /tmp/application/
COPY model /tmp/model/
COPY repository /tmp/repository/
COPY rest /tmp/rest/
COPY service /tmp/service/
COPY service.impl /tmp/service.impl/

WORKDIR /tmp/
RUN mvn clean install

FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:1.17-1.1693366274
COPY --chown=185 --from=MAVEN_TOOL_CHAIN /tmp/application/target/ms-account-service.jar app.jar

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]