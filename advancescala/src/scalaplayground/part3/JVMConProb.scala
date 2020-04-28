package scalaplayground.part3

object JVMConProb extends App {
  // DATA RACE CONDITION (JVM CONCURRENCY PROBLEMS)
  def runInParallel:Unit= {
    var newAmont = 1000
    var th1 = new Thread(new Runnable {
      override def run(): Unit = newAmont = 2000
    })
    var th2 = new Thread(new Runnable {
      override def run(): Unit = newAmont= 3000
    })
    th1.start()
    th2.start()
    println(newAmont)
  }
  for(_ <- 1 to 100) runInParallel
}
