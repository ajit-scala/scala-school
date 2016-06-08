import java.io.Serializable

import model.KafkaPartitionOffsets

val x = KafkaPartitionOffsets("test",101,1)
val x2 = KafkaPartitionOffsets("test2",102,2)
val x3 = KafkaPartitionOffsets("test3",103,3)


val l=List(x,x2,x3)// filter (x=>x.topic=="testy" && x.partitionId==102)
val l2=List(x,x2) filter (x=>x.topic!="test3" && x.partitionId!=103)

x3 :: l2


val l3: Product with Serializable = l match {
  case `x2`=>KafkaPartitionOffsets("test2",102,5)
  case y=>y
}
l.isEmpty


val m =Map("test_101" -> x, "test2_102" -> x2)
val m2 = m ++ Map("test_103"-> x3)
val m3 = m2.filter(x=>x._1!="test_103") ++ Map("test_103" -> KafkaPartitionOffsets("test3",103,5))

