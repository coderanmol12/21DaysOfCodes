package scalaplayground.part5

object SelfTypes extends App{

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
}
