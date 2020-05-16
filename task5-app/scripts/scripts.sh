minikube addons enable ingress

kubectl create namespace auth
kubectl config set-context --current --namespace=auth

helm dependency update .
helm create application-chart
