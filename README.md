# Microservices on Kubernetes


This is an example of how to run a microservices using Spring Boot and Couchbase on Kubernetes, the cool part of it is
that you easily scale both your application and database.

Here I assume that you already have a real Kubernetes cluster up and running (I haven't tested on minikube yet)

All yaml files are inside the "kubernetes" folder



# How to run this demo:


## Deploying the database ###########

1.  Run the command:
* `kubectl create -f admission.yaml`
* `kubectl create -f crd.yaml`
* `kubectl create -f operator-role.yaml`
* `kubectl create -f operator-service-account.yaml`
* `kubectl create -f operator-role-binding.yaml`
* `kubectl create -f operator-deployment.yaml`
* `kubectl create -f secret.yaml`
* `kubectl create -f couchbase-cluster.yaml`


## Deploying the Application

The docker image is public, so you don't need to touch the application (unless you would like to change something)

1. Create the secret to store the database's password
`kubectl create -f spring-boot-app-secret.yaml`

2. Deploy the application:
`kubectl create -f spring-boot-app.yaml`

3. Create a load balancer to access your microservice externally:
`kubectl create -f spring-boot-load-balancer.yaml`

4. The load-balancer might take a while to be available (at least 5 minutes during my tests). Once it is up, get
the External IP by running the following command:
`kubectl describe service spring-boot-load-balancer`

	It will return something like:


        Name:                     spring-boot-load-balancer
        Namespace:                default
        Labels:                   <none>
        Annotations:              <none>
        Selector:                 app=spring-boot-app
        Type:                     LoadBalancer
        IP:                       10.3.0.57
        LoadBalancer Ingress:   YOUR_EXTERNAL_IP_HERE
        Port:                     http  8080/TCP
        TargetPort:               8080/TCP
        NodePort:                 http  32340/TCP
        Endpoints:                10.2.2.7:8080
        Port:                     management  8081/TCP
        TargetPort:               8081/TCP
        NodePort:                 management  31415/TCP
        Endpoints:                10.2.2.7:8081
        Session Affinity:         None
        External Traffic Policy:  Cluster
        Events:                   <none>



Then, go to http://YOUR_EXTERNAL_IP_HERE:8080/ using your browser and congratulations! your Microservice is up and running.

You can call all the Restful endpoints from this app and Scale up and Down your Database and Application


## Scaling Up and Down

### Scaling the application:
1. To scale Up/Down you application, all you need to do is to change the number of replicas in the spring-boot-app.yaml file:
        ...
        spec:
          selector:
            matchLabels:
              app: spring-boot-app
          replicas: 3 #It was 1 before

2. Now replace the configuration
`kubectl replace -f spring-boot-app.yaml`


### Scaling the database:

1. To scale Up/Down Couchbase, just change the number of servers inside the couchbase-cluster.yaml:

        ...
        servers:
            - size: 6 #It was 3 before


2. Now replace the configuration
`kubectl replace -f 6-couchbase-cluster.yaml`
