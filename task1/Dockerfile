FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task1/
COPY target/task1_lib .
COPY target/task1.jar .

CMD java -jar task1.jar