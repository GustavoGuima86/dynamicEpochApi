# dynamicEpochApi

This is the second part of the project

In ths API we listening a RabbitMq topic and input the data in a relational in memory database 

In a second part we have 2 endpoints to
  - recovery the raw data by filter 
  - get the heart rate by filter

this project is running over Java 11 and gradle
not containerized (but could be possible and fine to have a Docker-Compose creating both projects and the RabbitMq)

this is a simple example, if I could make the necessary changes to put it to run I probably use

Cluster for entrypoints running in a top of a load balancer
The topic depends of the throuput should be a kafka or even a SQS
The dynamicEpochApi should the another container cluster 
The database could be a non relational one, but in this case I chosed a ralational because this data seems better in a relational in case we need perform complexy queries


