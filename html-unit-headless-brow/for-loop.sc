 for {  i <- 1 to 5
} yield i

 for {  i <- 1 to 5
 }println(i)


val x = 5
s"val  of x is $x"
f"val  of x is ${x}%03d"

 def getSum(items: Int*): Int ={
   var sum: Int =0
   for(num <- items){
     sum += num
   }
   sum
 }

 getSum(5,6,67,44,22,13)

def abc(f:(Int,String) => String): String = {
  f(101, "this is")
 }

 def testM(i:Int,s:String): String = {
   s+"::"+i
 }
 def testM1(i:Int,s:String): String = {
   s+"::::::::--:::::"+i
 }

 abc(testM)
 abc(testM1)