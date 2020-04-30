package scalaplayground.part3
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}

// important for futures
import scala.concurrent.ExecutionContext.Implicits.global


object FuturesPromises extends App {

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) =
      println(s"${this.name} poking ${anotherProfile.name}")
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )
    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )
    val random = new Random()


    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      // fetching from the DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
//    mark.onComplete(_ match {
//      case Success(markProfile) => {
//        val bill = SocialNetwork.fetchBestFriend(markProfile)
//        bill.onComplete(_ match {
//          case Success(billProfile) => markProfile.poke(billProfile)
//          case Failure(e) => e.printStackTrace()
//        })
//      }
//      case Failure(ex) => ex.printStackTrace()
//    })

  var friendsWall = mark.map(profile => SocialNetwork.fetchBestFriend(profile))
  for{
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    friend <- SocialNetwork.fetchBestFriend(mark)
  } yield  mark.poke(friend)
  Thread.sleep(2000)

}
