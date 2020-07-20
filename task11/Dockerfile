FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task11/
COPY target/task11_lib .
COPY target/task11.jar .
COPY jdbc.properties .

CMD java -jar task11.jar