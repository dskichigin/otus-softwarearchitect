FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task3/
COPY target/task3_lib .
COPY target/task3.jar .
COPY jdbc.properties .

CMD java -jar task3.jar