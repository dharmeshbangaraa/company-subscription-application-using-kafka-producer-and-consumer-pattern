# **Company Subscription Application using Kafka**

> Technologies used
```
Java 17 
Springboot 3
Kafka
Maven
```

> Tools used
```
Intellij IDE
Postman
H2 in memory db
```

> Basic setup & Installations to run the application
```
1. Clone the repo in your favourite IDE
2. Download [Kafka](https://kafka.apache.org/quickstart).
```

> To Run the Zookeeper & Kafka Server
### Open terminal and navigate to the folder where you have installed kafka
> Use `bin/zookeeper-server-start.sh config/zookeeper.properties` to start the zookeeper service. </br>
> Use `bin/kafka-server-start.sh config/server.properties` to start the kafka broker service in new tab/terminal. </br>

> [!NOTE]
> If you are on Windows navigate to Windows directory inside kafka folder to find above files.

> [!TIP]
> You can create topics, run producers and consumer on your terminal itself. Refer the quickstart guide.

### High Level Diagram explaining the events flows.
![image](https://github.com/dharmeshbangaraa/company-subscription-application-using-kafka-producer-and-consumer-pattern/assets/62675598/103d64b1-d4e8-433e-8565-4334621cd0b6)


