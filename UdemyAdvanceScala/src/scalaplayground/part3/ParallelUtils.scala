package scalaplayground.part3

import java.util.concurrent.atomic.AtomicReference

object ParallelUtils extends  App{

  // Parallel Utils


  var li= (1 to 100000000).toList
  println("the length of li is"+li.size)

  def measure[T](operaatio: => Unit)={
    val startTime= System.currentTimeMillis()
    operaatio
    println("Time took "+ (System.currentTimeMillis() - startTime) )
  }

//  measure{
//     li.map(_+1)
//  }
  measure(li.par.map(_+1))




  // ATOMIC OPS

  var atomic = new AtomicReference[Int](2)
  println(atomic.get())   // thread safe get
  atomic.set(3) //thread safe set (no other threads will access it when its doing get() and set())
  atomic.compareAndSet(3, 6)

}
