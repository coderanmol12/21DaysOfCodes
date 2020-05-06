## KAFKA BASIC 
Kafka is often used in real-time streaming data architectures to provide real-time analytics.




##### Most frequently used KAFKA-CLI Commands: 

- kafka-server-start.bat config/server.properties
- zookeeper-server-start.bat config/server.properties
- kafka-topics --zookeeper localhost:2181 --topic first_topic --create --replication-factor 1 --partitions 3   //Make first topic
- kafka-topics --zookeeper localhost:2181 --list    //list topics
- kafka-console-producer --broker-list localhost:9092 --topic first_topic --producer-property acks=all   //producer cli
- kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning           // consumer cli
- kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning --group my-first-application    // CONSUMER GROUP CLI
- kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group -my-first-application    
- kafka-consumer-groups --bootstrap-server localhost:9092  --group -my-first-application --reset-offsets --to-earliest --execute --topic first_topic
   


#### SBT DEPENDENCY :  
 -  "org.apache.kafka" % "kafka-clients" % "1.0.0"
 
#### MAVEN DEPENDENCY 
   -   <dependency>
         <groupId>org.apache.kafka</groupId> <artifactId>kafka-clients</artifactId>  <version>2.0.0</version> </dependency>
                                                                                            
         
              
              