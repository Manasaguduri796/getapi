echo "create namespaces"
kubectl create namespace postgres-db
kubectl create namespace springboot-app
echo "configmaps"

kubectl apply -f config.yaml -n springboot-app
kubectl apply -f config.yaml -n postgres-db

echo "deploy postgres deployment"
kubectl apply -f postgres/deployment.yaml -n postgres-db
kubectl apply -f postgres/service.yaml -n postgres-db

echo "deploy springboot deployment"
kubectl apply -f springboot/deployment.yaml -n springboot-app
kubectl apply -f springboot/service.yaml -n springboot-app

echo "deployed successfully"

