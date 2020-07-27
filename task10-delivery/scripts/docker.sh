# построение и загрузка на dockerhub
docker build -t task10:deliveryservice .
docker tag <hash> dskichigin/otus-softwarearchitect:deliveryservice
docker push dskichigin/otus-softwarearchitect:deliveryservice
