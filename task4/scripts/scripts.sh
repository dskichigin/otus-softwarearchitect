minikube addons disable ingress

kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring

#prometheus bitnami
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install prom bitnami/prometheus-operator -f prometheus.yaml --atomic

#prometheus stable
# можно без реп
helm repo add stable https://kubernetes-charts.storage.googleapis.com
helm install prom stable/prometheus-operator -f prometheus.yaml --atomic

#nginx
# можно без реп
helm repo add nginx-stable https://helm.nginx.com/stable
helm repo update
helm install nginx nginx-stable/nginx-ingress -f nginx-ingress.yaml --atomic
# можно так
helm install nginx stable/nginx-ingress -f nginx-ingress.yaml --atomic

#port forwarding gafana
kubectl port-forward service/prom-grafana 9000:80
#grafana login:password
#admin:prom-operator

#port forwarding gafana
kubectl port-forward service/prom-prometheus-operator-prometheus 9090

#servicemonitor list
kubectl get servicemonitors.monitoring.coreos.com
kubectl describe servicemonitors.monitoring.coreos.com

helm install userservice-chart .
kubectl scale --replicas=0 deployment/userservice-chart


helm dependency update .


#grafana dashboard postgresql 9628 6742