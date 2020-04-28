package scalaplayground.part2

object LazyEvaluation extends App{


  lazy val lazyCodition:Boolean ={
    print("Hello")
    true
  }

  // doesn't print Hello since layConditon did not ran yet...
  print(if(false && lazyCodition) "yes" else "no")



  // CALL BY NEED
  def byName(n: =>Int):Int={
    lazy val t = n
    t+t+t+1
  }

  def retriverNumber():Int={
    Thread.sleep(1000)
    println("Waiting")
    42
  }

  var lazyNum= retriverNumber()
  print(byName(lazyNum))







}
