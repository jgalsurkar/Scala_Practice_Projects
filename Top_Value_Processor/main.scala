import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.ListBuffer

import scala.collection.concurrent.TrieMap
import scala.io.Source
import scala.collection.mutable.PriorityQueue
import scala.actors.Actor._

object main {
  def main(args: Array[String]): Unit = {
   
   //Create new instance of TopValueProcessor, passing in (file name, draws) from the command line
   val bigData = new TopValueProcessor(args(0), args(1).toInt)
   
   //Process the data
   bigData.processData()
   println("Setting Distributions")
   //Setting the Hyper Geometric Distributions for the Data
   bigData.setDistrib()
   println("Sorting Distributions")
   //Sorting the categories based on their Hyper Geometric Distributions
   val finalList = bigData.sortDistributions()
   
   //Print out results
   finalList.foreach { x => x.printStats() }
  }
}