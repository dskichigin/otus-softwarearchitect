FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task5/
COPY target/task5_lib .
COPY target/task5.jar .
COPY jdbc.properties .

CMD java -jar task5.jar