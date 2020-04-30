package scalaplayground.part3

import java.util.Random

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object PromiseFuture4 extends App {


  /**
   * Exercise 1
   * 1 Write a future which will return a value immediately.
   * 2 Insequence(fa, fb)
   * 3 retryUntil[T](action: () =>F[T], condition : T => Boolean):Future[T]
   * 4 first[T](fa,fb):f[T] which future will complete first it will return respectibe future value
   */

  //1
  def immediateFuture(): Future[String] = Future("ehello")

  //2
  def inSequence(fa: Future[String], fb: Future[String]): Future[String] = fa.flatMap(_ => fb)

  //3

  def retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T] = {
    action().
      filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }
  }

  var random = new Random()
  var action = () => Future {
    println("in action")
    Thread.sleep(1000)
    var value = random.nextInt(10)
    println("generated value " + value)
    value
  }

  //  var cnd = (x:Int)=> x==2
  //  retryUntil[Int](action,cnd)
  //  Thread.sleep(20000)


  def first[T](fa: Future[T], fb: Future[T]): Future[T] = {
    val promise = Promise[T]()

    def tryCompletes(promise:Promise[T], result: Try[T])= result match {
      case Success(r) => promise.success(r)
      case Failure(exception) => promise.failure(exception)
    }
    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)
    promise.future
  }


}

