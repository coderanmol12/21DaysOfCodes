package scalaplayground.part4

object MagnetoPattern extends App{

  trait MathLib{
    def add1(s:Int):Int=s+1
    def add1(s:String):Int= s.toInt+1
  }

  trait AddMagnet{
    def apply():Int
  }

  def add1(magnet: AddMagnet):Int= magnet()

  implicit class AddInt(x:Int)extends AddMagnet{
    override def apply(): Int = x+1
  }

  implicit class AddString(x:String)extends AddMagnet{
    override def apply(): Int = x.toInt+1
  }

  var addFv= add1 _
  println(addFv(1))
  println(addFv("2"))


}
