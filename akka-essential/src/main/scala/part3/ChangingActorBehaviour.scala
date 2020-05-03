package part3

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ChangingActorBehaviour extends App{


  val actorSystem = ActorSystem("behaviourChange")

  object Kid{
    case object KidAccept
    case object KidReject
    val HAPPY = "happy"
    var SAD = "sad"
  }

  object Mom{
    val VEGETABLE = "VEGETABLE"
    val CHOCLATE = "CHOCLATE"
    case class Food(food:String)
    case class Ask(message:String)   // always be a question
  }


  class Kid extends Actor{
    import Mom._
    import Kid._
    var state= HAPPY

    override def receive: Receive = {
      case Food(VEGETABLE) => {
        state = SAD
      }
      case Food(CHOCLATE) => {
        state = HAPPY
      }
      case Ask(_) => {
        if (state== HAPPY) sender() ! KidAccept
        else sender() ! KidReject
      }
    }
  }



  class FussyKid extends Actor{
    import Mom._
    import Kid._
    var state= HAPPY

    override def receive: Receive = happyRecieve
    def happyRecieve: Receive={
      case Food(VEGETABLE) => {
        state = SAD
        context.become(unhappyRecieve,false)
      }
      case Food(CHOCLATE) => {
        state = HAPPY
      }

      case Ask(_) => {
         sender() ! KidAccept

      }

    }

    def unhappyRecieve: Receive={
      case Food(VEGETABLE) => {
        state = SAD
      }
      case Food(CHOCLATE) => {
        context.unbecome()
      }
      case Ask(_) => {
        sender() ! KidReject
      }
    }

  }

  class Mom extends Actor{
    import Kid._
    import Mom._
    override def receive: Receive = {
      case kidref: ActorRef => {
        kidref ! Food(VEGETABLE)
        kidref ! Food(CHOCLATE)
        kidref ! Food(VEGETABLE)
        kidref ! Food(CHOCLATE)
        kidref ! Ask("do you want to study ???")
      }
      case KidAccept => print("kid will do ")
      case KidReject => print("kid will not do")
    }
  }

  var mom = actorSystem.actorOf(Props[Mom])
  var kid = actorSystem.actorOf(Props[Kid])
  var fussyKid = actorSystem.actorOf(Props[FussyKid])

  //mom ! kid
  mom ! fussyKid
}
