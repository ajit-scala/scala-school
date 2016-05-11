object Hello {
    def main(args:Array[String]): Unit = {
        val l =  List(1,2,3,4,5)
        val x = l.map(x=>x*2)
        //println(x) 
        
        def f(x:Int)=if(x>2) Some(x) else None
        val xx = l.map(x=>f(x))
        //println(xx) 

        
        def myFun(i:Int) = Seq(i-1,i,i+1)
        val m=l.map(x=>myFun(x))
        val fm=l.flatMap(x=>myFun(x))
        println(m)
        println(fm)
    }
}