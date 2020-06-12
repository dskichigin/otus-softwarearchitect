FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task6/
COPY target/task6_lib .
COPY target/task6.jar .
COPY jdbc.properties .

CMD java -jar task6.jar