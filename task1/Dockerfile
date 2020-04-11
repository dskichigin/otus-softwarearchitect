FROM adoptopenjdk/openjdk8:alpine-slim
EXPOSE 8000
WORKDIR /app/task1/
COPY target/task1_lib .
COPY target/task1.jar .
COPY start.sh .
RUN chmod 775 start.sh

CMD ./start.sh