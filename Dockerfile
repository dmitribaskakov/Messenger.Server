FROM openjdk:15

EXPOSE 19000

RUN mkdir /app

COPY target/Messenger.Server-1.0.jar /app

WORKDIR /app

CMD java -jar Messenger.Server-1.0.jar