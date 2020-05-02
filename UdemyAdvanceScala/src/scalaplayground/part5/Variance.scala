package scalaplayground.part5

object Variance extends App{


  trait Animal
  trait Cat extends Animal
  trait Dog extends Animal
  trait Crocodile extends Animal

  class CCage[+T]     // COVARIANCE
  var ccage: CCage[Animal]= new CCage[Cat]

  class ICage[T]        // INVARIANCE
  //var icage: ICage[Animal]= new ICage[Cat] // NOT POSSIBLE
  var icage:ICage[Cat]= new ICage[Cat]

  class XCage[-T]        // CONTRAVARIANCE
  var xcage:XCage[Cat]= new XCage[Animal]



  // EXERCISE

  trait Vehicle
  trait Bike extends Vehicle
  trait Car extends Vehicle
  class IList[T]

  class IParking[T](vehicles: List[T]){

    def park(vehicle: T):IParking[T] = ???
    def impound(vehicles:List[T]): IParking[T] = ???
    def checkForVehicles(condition:String):IParking[T] = ???
  }
  class CParking[+T](vehicles: List[T]){
    def park[S >: T](vehicle: S):CParking[S] = ???
    def impound[S >: T](vehicles:List[S]): CParking[S] = ???
    def checkForVehicles[S >: T](condition:String):CParking[S] = ???
  }

  class COParking[-T](vehicles: List[T]){
    def park(vehicle: T):COParking[T] = ???
    def impound[S <: T](vehicles:List[S]): COParking[S] = ???
    def checkForVehicles[S <: T](condition:String):COParking[S] = ???
  }








}
