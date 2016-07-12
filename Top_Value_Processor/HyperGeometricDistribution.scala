package HyperGeometricDistribution

import scala.math.BigDecimal

object HyperGeometricDistribution {
 
   def hyperGeoDistrib(popSize : Int, successStates: Int, draws:Int, sucesses:Int): BigDecimal = {
    (binomialCoeff(successStates, sucesses) * binomialCoeff(popSize - successStates, draws - sucesses))/binomialCoeff(popSize, draws)
  }
  
  private def binomialCoeff(n: Int, k: Int): BigDecimal = {   
     factorial(n) / (factorial(k) * factorial(n - k)) 
  }
 
  private def factorial(n: Int): BigDecimal = {
    @annotation.tailrec
    def fact(n: BigDecimal, acc: BigDecimal): BigDecimal = {
      if (n == 0) acc
      else 
        fact(n - 1, n * acc)
    }
    fact(n, 1)
  }
}