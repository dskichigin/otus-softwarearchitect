# построение и загрузка на dockerhub
docker build -t task10:warehouseservice .
docker tag <hash> dskichigin/otus-softwarearchitect:warehouseservice
docker push dskichigin/otus-softwarearchitect:warehouseservice
