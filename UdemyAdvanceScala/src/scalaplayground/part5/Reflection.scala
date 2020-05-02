package scalaplayground.part5

import scala.reflect.runtime.{universe => ru} //TYPE ALIAS (short hand notation)


object Reflection extends App {

  case class Person(name: String) {

    def sayHello(): Unit = {
      println(s"my name is $name")
    }
  }


  val m = ru.runtimeMirror(getClass.getClassLoader)

}
