# построение и загрузка на dockerhub
docker build -t task10:orderservice .
docker tag <hash> dskichigin/otus-softwarearchitect:orderservice
docker push dskichigin/otus-softwarearchitect:orderservice
