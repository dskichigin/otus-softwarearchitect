kubectl get namespaces
kubectl config set-contex --current --namespace=myapp

docker build -t task:v1 ../task1/.
kubectl apply -f deployment.yaml -f service.yaml


docker images
docker image rm ...
docker ps

kubectl delete all --all

minikube start --vm-driver=virtualbox
minikube docker-env
minikube addons list
minikube addons enable ingress
