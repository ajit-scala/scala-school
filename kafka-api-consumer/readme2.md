# Kafka on AWS Cluster


## Version Info

* The used Kafka version is 0.9.0.1. 
  for up-to-date version infos.

## Using and Connecting

* In order to access ZooKeeper and the Kafka cluster, you need to be within the
  VPC and in the security group ZooKeeperClientSecurityGroup (output `ZooKeeperClientSecurityGroup` of `zookeeper-client` CloudFormation stack).
  and the security group KafkaClientSecurityGroup (output `KafkaClientSecurityGroup` of `kafka-client` CloudFormation stack).

* ZooKeeper can be accessed at zookeeper.aws.ajitchahal.com, port 2181. Testing
  the connection can be done by sending a [four letter word](http://zookeeper.apache.org/doc/r3.4.8/zookeeperAdmin.html#sc_zkCommands): `echo ruok | nc -w 10 zookeeper.aws.ajitchahal.com 2181`. Expected is an answer `imok` *without newline*.

* The ZooKeeper connection string can be created using
  `host zookeeper.aws.ajitchahal.com | awk -vORS=:2181, '{ print $4 }' | sed 's/,$//'`
  for ajitdev account, and
  `host zookeeper.a.ajitchahal.com | awk -vORS=:2181, '{ print $4 }' | sed 's/,$//'`
  for ajitprod account.
  We try to keep these IPs stable. They will stay stable unless there is
  justified reason for them to change.

* For the Kafka broker connection string, you can either use
  * for ajitdev: `broker1.kafka.aws.ajitchahal.com:9092,broker2.kafka.aws.ajitchahal.com:9092,broker3.kafka.aws.ajitchahal.com:9092,broker4.kafka.aws.ajitchahal.com:9092,broker5.kafka.aws.ajitchahal.com:9092`
  * for ajitprod: `broker1.kafka.a.ajitchahal.com:9092,broker2.kafka.a.ajitchahal.com:9092,broker3.kafka.a.ajitchahal.com:9092,broker4.kafka.a.ajitchahal.com:9092,broker5.kafka.a.ajitchahal.com:9092`
* Or use the private IPs directly with
  `host kafka.aws.ajitchahal.com | awk -vORS=:9092, '{ print $4 }' | sed 's/,$//'`
  for ajitdev account, and
  `host kafka.a.ajitchahal.com | awk -vORS=:9092, '{ print $4 }' | sed 's/,$//'`
  for ajitprod account.
  We try to keep these IPs stable. They will stay stable unless there is
  justified reason for them to change.

## Topic Replication and Acknowledgement Configuration

We advise to use a two-out-of-three replicas majority vote (topics should be
configured as described [here](#creating-listing-and-deleting-topics)) for
producer acknowledgement (producers should user `acks=all`). This guarantees
consistency when facing multiple broker failures, and availability in the face
of a single broker failure.

**Important**: In order to be (relatively) safe in the face of potential chaos
monkey activity and rolling updates, when you use `acks=1` use a **replication
factor of 4** when creating topics. It is generally not advisable to use
`acks=1` for critical data.

## Creating, Listing, and Deleting Topics

The [Kafka binary distribution](http://kafka.apache.org/downloads.html) can
be used to manage topics, the Scala object
[kafka.admin.TopicCommand](https://github.com/apache/kafka/blob/0.9.0/core/src/main/scala/kafka/admin/TopicCommand.scala#L37),
and the Scala object
[kafka.admin.AdminUtils](https://github.com/apache/kafka/blob/0.9.0/core/src/main/scala/kafka/admin/AdminUtils.scala#L43)
directly from the Kafka library. The following discuses how to manage topics
with the [kafka-topics.sh](https://github.com/apache/kafka/blob/0.9.0/bin/kafka-topics.sh)
script of the binary distribution.

Assuming the following bash environment

```
ZOOKEEPER='zookeeper.aws.ajitchahal.com:2181'
TOPIC_NAME=test
```

To create a Kafka topic with log compaction enabled, use (and modify parameters
accordingly)

```
bin/kafka-topics.sh --create --topic $TOPIC_NAME --zookeeper $ZOOKEEPER \
 --replication-factor 3 --partitions 30 --config min.insync.replicas=2 --config cleanup.policy=compact
```

Log compaction is now the default cleanup policy. To use time-based log cleanup,
use `cleanup.policy=delete` instead.

To list all existing Kafka topics, use

```
bin/kafka-topics.sh --list --zookeeper $ZOOKEEPER
```

To delete a Kafka topic, use (at your own discretion)

```
bin/kafka-topics.sh --delete --topic $TOPIC_NAME --zookeeper $ZOOKEEPER
```


## Monitoring and Inspection

* The [Exhibitor UI](https://exhibitor.aws.ajitchahal.com/exhibitor/v1/ui/index.html)
  can be accessed from the Office network. Exhibitor allows you to *modify*
  the ZooKeeper configuration. Please refrain from doing so, and stay in
  read-only mode when using the Exhibitor UI. If you have reason to depart from
  this recommendation, please get in touch with the SharedServices team via
  e.g. [Slack](https://ajit.slack.com/messages/sharedservices/).

* ZooKeeper logs can be found in [Kibana dev](https://log2es-kibana.aws.ajitchahal.com/app/kibana#/discover/SharedServices-ZooKeeper)
and [Kibana prod](https://log2es-kibana.a.ajitchahal.com/app/kibana#/discover/SharedServices-ZooKeeper).

* Kafka logs can be found in [Kibana dev](https://log2es-kibana.aws.ajitchahal.com/app/kibana#/discover/SharedServices-Kafka)
and [Kibana prod](https://log2es-kibana.a.ajitchahal.com/app/kibana#/discover/SharedServices-Kafka).

* Metrics can be found in [Instana](https://ajitchahal-ajitchahal.instana.io/#/)
  when filtering by tag "Kafka".

## Docker

### Prerequisites

* [Docker](https://www.docker.com/) 1.10 or later
* [Docker compose](https://docs.docker.com/compose/overview/) 1.6 or later
* Ruby

### Run

Create a three node Kafka cluster with the latest published images:

```
aws_assume ajitdev ReadOnlyAccess
$(aws ecr get-login)
./local-cluster-up 3
```

Listing container and port mappings

```
$ docker ps
CONTAINER ID        IMAGE                                                                     COMMAND                  CREATED             STATUS              PORTS                                            NAMES
d9f266bc6f71        544725753551.dkr.ecr.eu-west-1.amazonaws.com/kafka/kafka:latest           "./wait-for-it.sh zoo"   3 seconds ago       Up 3 seconds        9092/tcp, 0.0.0.0:9094->9094/tcp                 local_kafka_3_1
89ea5d0cfe58        544725753551.dkr.ecr.eu-west-1.amazonaws.com/kafka/kafka:latest           "./wait-for-it.sh zoo"   4 seconds ago       Up 3 seconds        9092/tcp, 0.0.0.0:9093->9093/tcp                 local_kafka_2_1
9ef38519e30b        544725753551.dkr.ecr.eu-west-1.amazonaws.com/kafka/kafka:latest           "./wait-for-it.sh zoo"   4 seconds ago       Up 3 seconds        0.0.0.0:9092->9092/tcp                           local_kafka_1_1
8c8dad7e862e        544725753551.dkr.ecr.eu-west-1.amazonaws.com/zookeeper/zookeeper:latest   "/bin/sh -c 'java -Xm"   4 seconds ago       Up 4 seconds        0.0.0.0:2181->2181/tcp, 0.0.0.0:8080->8080/tcp   local_zookeeper_1
```

To create a cluster with different sizing, specify the desired number of brokers.

### Testing Examples

Connect to running Kafka instance to access the Kafka scripts

```
docker exec -it local_kafka_1_1 /bin/bash
```

Create a Kafka test topic

```
./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic test --partitions 3 --replication-factor 3 --config cleanup.policy=delete --config min.insync.replicas=2
```

Producing some test messages

```
./bin/kafka-console-producer.sh --topic test --broker-list localhost:9092

# Type some messages and hit enter (Quit with Ctrl+c)
```

Consuming the test messages:

```
./bin/kafka-console-consumer.sh --zookeeper zookeeper:2181 --topic test --from-beginning
```

### Shutting Down

Deleting the local cluster (including all data contained):

```
./local-cluster-down
```

### With Docker Images from your Local Registry

build:

```
docker build -t kafka:local -f Dockerfile_kafka .
```

run:

```
KAFKA_IMG=kafka:local ./local-cluster-up 5
```

You can also define the ZooKeeper image to use by setting `ZK_IMG`

## Owner

Team owning this repository: SharedServices
<#ajit-SharedServices-ds@ajit.com>
