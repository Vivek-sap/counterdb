# [Counter Service/DB ]

Counter service/db suffice following usecase
- Should increment a counter
- Should decrement a counter
- Should return the value of the counter when asked for
- When having n deployments in the cluster with the given label, the counter service should
return n via the implemented REST endpoint.

### Technology Used

* Spring boot
* Mysql  
* lombok
* Java 8 
* flyway

### Rest endpoints
* PATCH  local: `http://{minikube-ip}:{port}/v1/counter/increment/{val}`
* PATCH  local: `http://{minikube-ip}:{port}/v1/counter/decrement/{val}`
* GET    local: `http://{minikube-ip}:{port}/v1/counter/`
* GET    local: `http://{minikube-ip}:{port}/v1/deployment/namespace/{namespace}/label/labelValue/{labelkey}/labelValue/{labelValue}`

content-type: application/json

### Why used Database
 Counter service supposed to be stateless hence if it deployed in multiple pod then increment/decrement api should share some storage/cache. 
 
### Usage

##### Compile
```
mvn clean install
mvn package

```

#####  Docker image generation
```
Example
docker import mysql:5.7

docker build -t springboot-k8s-mysql:v1.0 .

```
### Deployment

All the yaml file are defined in deployment folder of the project. 

##### prerequisite
minikube or any other kubernetes tool install locally. Run the script accordingly 


```
eval $(minikube docker-env)

Configmap Deployment
--------------------
kubectl apply -f mysql-configmap.yml
kubectl apply -f controller-configmap.yml

Secrets Deployment
--------------------
kubectl apply -f mysqldb-root-credentials.yml
kubectl apply -f mysqldb-credentials.yml

Deployment
--------------------
kubectl apply -f mysql-deployment.yml
kubectl apply -f deployment.yml
```



### Improvement
Counter Service and Mysql service can be decoupled!!!
