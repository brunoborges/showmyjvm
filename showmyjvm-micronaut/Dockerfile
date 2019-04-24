FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/showmyjvm-micronaut*.jar showmyjvm-micronaut.jar
CMD java ${JAVA_OPTS} -jar showmyjvm-micronaut.jar