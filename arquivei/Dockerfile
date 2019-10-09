FROM openjdk:8-jdk-alpine
LABEL maintainer="luizusp@gmail.com"
VOLUME /tmp
EXPOSE 8085
COPY ./target/arquivei-0.0.1-SNAPSHOT.jar arquivei-nfeMessage-consumer.jar
ENTRYPOINT ["java","-jar","/arquivei-nfeMessage-consumer.jar"]


