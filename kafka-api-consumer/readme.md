## Kafka consumer:
A kafka stream consumer in scala using kafka's own consumer api.

 #  Todo:
Queue messages coming in if you want to batch write to dynamo db:
### Java's ArrayBlockingQueue could be used
 - blocks producer of queue if capacity of queue is full so the producer thread stops and wait to fecth further items
 - Bcan block consumer if there is nothing to consumer, in a while loop poll machanism
