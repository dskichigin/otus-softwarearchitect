helm create paymentservice
helm create deliveryservice
helm create warehouseservice
helm create orderservice

helm dependency update deliveryservice/.
helm install deliveryservice deliveryservice/.

helm dependency update paymentservice/.
helm install paymentservice paymentservice/.

helm dependency update warehouseservice/.
helm install warehouseservice warehouseservice/.

helm dependency update orderservice/.
helm install orderservice orderservice/.

skaffold deploy
