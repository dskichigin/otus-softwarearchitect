helm create productservice-chart
helm dependency update .
helm install productservice-chart .

kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring

#port forwarding gafana
kubectl port-forward service/prom-grafana 9000:80
#grafana login:password
#admin:prom-operator

#port forwarding gafana
kubectl port-forward service/prom-prometheus-operator-prometheus 9090
