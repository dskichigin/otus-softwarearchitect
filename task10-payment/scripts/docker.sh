# построение и загрузка на dockerhub
docker build -t task10:paymentservice .
docker tag <hash> dskichigin/otus-softwarearchitect:paymentservice
docker push dskichigin/otus-softwarearchitect:paymentservice
