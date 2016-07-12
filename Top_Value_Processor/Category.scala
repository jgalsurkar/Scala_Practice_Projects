

import scala.math.BigDecimal

//This class defines a category which contains a name, total count, and the amount of successes in the top draws
class Category (name : String, count : Int, successes : Int){
  
  private val Name = name
  private var Count = count
  private var Successes = successes
  private var hyperGeoDistribution = BigDecimal(0.0)
  
  //Getter & Setter Functions Below
  
  def getName() = Name
  
  def getCount() = Count
  
  def getSuccesses() = Successes
  
  def getHyperGeo() = hyperGeoDistribution

  def setCount(n : Int) : Unit = {Count = n}
  
  def setSuccesses(n : Int) : Unit = {Successes = n}
  
  def setHyperGeo(n : BigDecimal) : Unit = {hyperGeoDistribution = n}

  //Increment count/successes
  
  def addCount(n : Int) = {Count += n}
  
  def addSuccesses(n : Int) = {Successes += n}
  
  def < (that: Category): Boolean = this.hyperGeoDistribution < that.hyperGeoDistribution
  
  def printStats() = { 
    println("Hyper Geometric Distribution: " + hyperGeoDistribution + " of Category " + Name) 
  }
}