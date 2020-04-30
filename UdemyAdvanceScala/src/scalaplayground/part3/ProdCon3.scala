package scalaplayground.part3
import scala.collection.mutable

object ProdCon3 extends App {


  def prodMultipleCons():Unit={
    var buffer: mutable.Queue[Int]= new mutable.Queue[Int]
    var i =0
    var capacity = 3
    var producer = new Thread(new Runnable {
      override def run(): Unit = {
        while (true){
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[Producer] is full")
            buffer.wait()
          }
          buffer.enqueue(i)
          buffer.notifyAll()
          i=i+1
        }
        Thread.sleep(250)
      }}
    })


    var consumer1 = new Thread(new Runnable {
      override def run(): Unit = {

        while (true) {
          buffer.synchronized {

            if (buffer.isEmpty) {
              println("[Consumer1] buffer is empty")
              buffer.wait()
            }
            var x = buffer.dequeue()
            println("[Consumer 1] buffer is deqeqed with value" + x)
            buffer.notify()
          }
          Thread.sleep(50)
        }
      }
    })

    producer.start()
    consumer1.start()
  }
  prodMultipleCons()







}