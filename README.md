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
* Java 11 
* flyway

### Rest endpoints
* PATCH  local: `http://localhost:8080/v1/counter/increment/{val}`
* PATCH  local: `http://localhost:8080/v1/counter/decrement/{val}`
* GET    local: `http://localhost:8080/v1/counter/`
* GET    local: `http://localhost:8080/v1/deployment/namespace/{namespace}/label/labelValue/{labelkey}/labelValue/{labelValue}`

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
docker build -t customcontroller:v1.0 .

```
### Deployment

All the yaml file are defined in deployment folder of the project. One shell script is also provided to run everything in one go. 

#### commands


### Improvement

###