package scalaplayground.part5

object SelfTypes extends App{


  // If extending One behaviour(1)then you should also extend another mandatory behaviour require to achieve behaviour 1.
  trait InstrumentList{
    def play():Unit
  }


  // Must Know to play Instruments
  trait Singer{ self: InstrumentList =>
    def sing():Unit
  }

  class LeadSinger extends Singer with InstrumentList {
    override def sing(): Unit = ???
    override def play(): Unit = ???
  }


  class Guitarist extends InstrumentList {
    override def play(): Unit = println("sings")
  }

  var obj = new Guitarist with Singer{
    override def sing(): Unit = println("checking")
  }

  obj.sing()
}
