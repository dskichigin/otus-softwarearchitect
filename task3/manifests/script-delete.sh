kubectl delete -f postgres.yaml -f userservice-config.yaml -f initdb.yaml  -f deployment.yaml -f service.yaml -f ingress.yaml
#kubectl delete  pvc postgredb-postgres-statefulset-0