package scalaplayground.part3

import java.util.Random

import scala.collection.mutable

object ProdCon2 extends App {

  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    var cons = new Thread(new Runnable {
      override def run(): Unit = {

        val random = new Random()

        while(true) {
          buffer.synchronized {
            if (buffer.isEmpty) {
              println("[consumer] buffer empty, waiting...")
              buffer.wait()
            }

            // there must be at least ONE value in the buffer
            val x = buffer.dequeue()
            println("[consumer] consumed " + x)

            // hey producer, there's empty space available, are you lazy?!
            buffer.notify()
          }
          Thread.sleep(random.nextInt(250))
      }
    }})

    val producer = new Thread(new Runnable {
      override def run(): Unit = {
      val random = new Random()
      var i = 0
      while(true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full, waiting...")
            buffer.wait()
          }

          // there must be at least ONE EMPTY SPACE in the buffer
          println("[producer] producing " + i)
          buffer.enqueue(i)

          // hey consumer, new food for you!
          buffer.notify()

          i += 1
        }

        Thread.sleep(random.nextInt(500))
      }}
    })

    cons.start()
    producer.start()
  }

  prodConsLargeBuffer()
}