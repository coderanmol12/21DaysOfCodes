package scalaplayground.part1

object Singelton{
  // Varaibles of singleton object
  var str1 = "Welcome ! PART1";
  var str2 = "This is Advance Scala language tutorial";

  // Method of singleton object
  def display()
  {
    println(str1);
    println(str2);
  }
}

// Singleton object with named as Main
object Main
{
  def main(args: Array[String])
  {

    // Calling method of singleton object
    Singelton.display();
  }
}

