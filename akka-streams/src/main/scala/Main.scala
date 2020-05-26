import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object Main extends App{

  def check() :Future[Int] ={
    Future.successful(42)
  }

  check().onComplete{
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }

  Thread.sleep(1000)



}
