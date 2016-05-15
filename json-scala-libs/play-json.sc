import play.api.libs.json._
case class Model(hello: String, age: Int)
case class Emp(name:String, model:Model)

implicit val modelFormat = Json.format[Model]
implicit val empFormat = Json.format[Emp]

val m= Model("hello",1234)
val e = Emp("Ajit",m)


Json.toJson(e)

case class Car(name:String, model:Seq[Model])
implicit val carFormat = Json.format[Car]

val c = Car("BMW", List(m,Model("X1",12))Json.toJson(c)

