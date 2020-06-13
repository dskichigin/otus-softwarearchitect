#!/bin/bash
minikube addons disable ingress

kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring
helm install prom stable/prometheus-operator -f prometheus.yaml --atomic
helm install nginx stable/nginx-ingress -f nginx-ingress.yaml --atomic

helm install prom-postgresql-exporter stable/prometheus-postgres-exporter --atomic

#port forwarding gafana
kubectl port-forward service/prom-grafana 9000:80
#grafana login:password
#admin:prom-operator

#port forwarding gafana
kubectl port-forward service/prom-prometheus-operator-prometheus 9090
