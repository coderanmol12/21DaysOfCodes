import kamon.Kamon


object Metrics {  

  def counter(path: String, tags: (String, String)*) =  {
    Kamon.counter(path).refine(tags:_*).increment()
  }

  def record(path: String, value: Long, tags:(String, String)*) =  {
    Kamon.histogram(path).refine(tags:_*).record(value)
  }

}