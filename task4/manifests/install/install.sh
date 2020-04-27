#!/bin/bash

kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring
helm install prom stable/prometheus-operator -f prometheus.yaml --atomic
helm install nginx stable/nginx-ingress -f nginx-ingress.yaml --atomic

#helm install prom-postgresql-exporter stable/prometheus-postgres-exporter --atomic
