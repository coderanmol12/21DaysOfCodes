package scalaplayground.part3

object JVMThreadComm extends App{


  // The producer-consumer problem

  // Producer-Consumer problem can be solved using new Thread APIs
  /**
   * Synchonized, Wait, Notify
   * Coderman12@man
   * Wait: It will release the lock monitor and suspend
   * Notify: Notify the sleeping thread to continue
   * Monitor: The monitor is a data structure that it's internally used by the JVM to keep track of which object is
   * locked by which thread.
   */


  class SimpleContainer {

    var value =0
    def isEmpty:Boolean= value==0
    def set(num:Int):Unit = {value=num}
    def get():Int= {
      var result= value
      value=0
      result
    }
  }


  def smartProdConsumer():Unit={

    val container = new SimpleContainer
    var consumer = new Thread(new Runnable {
      override def run(): Unit = {
      println("[Consumer] is waiting ....")
      container.synchronized{
        container.wait()
      }
      println("I have consumed value: "+ container.value)
    }})

    var producer = new Thread(new Runnable {
      override def run(): Unit = {
        Thread.sleep(2000)

        container.synchronized {
          container.set(42)
          println("[Producer] produced new value")
          container.notify()
        }
    }
    })
    consumer.start()
    producer.start()

  }
  smartProdConsumer()
}
