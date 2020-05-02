package scalaplayground.part5
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HigherKindaTypes extends App{


  trait MyList[T]{
    def flatMap[B](f:T => B): MyList[B]
  }
  trait MyOption[T]{
    def flatMap[B](f:T => B): MyOption[B]
  }
  trait MyFuture[T]{
    def flatMap[B](f:T => B): MyFuture[B]
  }

  def multiply[A,B](lista:List[A],listb:List[B]):List[(A,B)]=for{
    a<- lista
    b<- listb
  } yield (a,b)

  def multiply[A,B](lista:Option[A],listb:Option[B]):Option[(A,B)]=for{
    a<- lista
    b<- listb
  } yield (a,b)
  def multiply[A,B](lista:Future[A],listb:Future[B]):Future[(A,B)]=for{
    a<- lista
    b<- listb
  } yield (a,b)


  trait Monad[F[_],A]{
    def flatMap[B](f: A => F[B]): F[B]
    def map[B](f:A => B ):F[B]
  }

  class MonadList(list:List[Int]) extends Monad[List,Int] {
    override def flatMap[B](f: Int => List[B]): List[B] = list.flatMap(f)
    override def map[B](f: Int => B): List[B] = list.map(f)
  }

  val monadList = new MonadList(List(1,2,3))
  monadList.flatMap(x => List(x,x+1))
  monadList.map(_*2)
}


