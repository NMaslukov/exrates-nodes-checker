FROM openjdk:8
VOLUME /tmp
ARG APP_PATH=/exrates-nodes-checker
ARG ENVIRONMENT

RUN mkdir -p exrates-nodes-checker
COPY ./target/exrates-nodes-checker.jar ${APP_PATH}/api-service.jar
COPY ./target/config/${ENVIRONMENT}/application.properties ${APP_PATH}/application.properties

WORKDIR ${APP_PATH}
RUN readlink -f application.properties
EXPOSE 8080
CMD java -jar api-service.jar

