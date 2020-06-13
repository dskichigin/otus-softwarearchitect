FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task7/
COPY target/task7_lib .
COPY target/task7.jar .
COPY jdbc.properties .

CMD java -jar task7.jar