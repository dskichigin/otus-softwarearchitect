FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task10/
COPY target/task10_lib .
COPY target/task10.jar .
COPY jdbc.properties .

CMD java -jar task10.jar