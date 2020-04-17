docker build -t task1:v1 .
docker run -d -p 80:80 task1:v1
docker run -it -p 80:80 task1:v1
docker stop cont_hash

docker pull nginx
docker push id/cont-name:ver

# построение и загрузка на dockerhub
docker build -t task1:v1 .
docker tag <hash> dskichigin/otus-softwarearchitect:dsktask1
docker login --username=dskichigin
docker push dskichigin/otus-softwarearchitect:dsktask1