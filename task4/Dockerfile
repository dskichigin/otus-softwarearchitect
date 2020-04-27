FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task4/
COPY target/task4_lib .
COPY target/task4.jar .
COPY jdbc.properties .

CMD java -jar task4.jar