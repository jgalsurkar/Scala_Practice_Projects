
import scala.io.Source
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.Map 
import scala.collection.mutable.ListBuffer
import HyperGeometricDistribution.HyperGeometricDistribution
import MergeSort.MergeSort
import scala.math.Ordered._

class TopValueProcessor (file: String, draws: Int) {

  private var mainPQ = new PriorityQueue[(Double, String)]() //Max Heap
  private var popSize = 0 //Size of the population
  private val topPoints = draws //Top k Points
  private var listOfDistributions = ListBuffer[Tuple2[BigDecimal, String]]() //Mutable list of distributions
  private var Categories = ListBuffer[Category]() //Mutable List of categories
  
  //Process a big data file, and create/update categories & max heap
  def processData() = {
    
    var dataPoint = new Array[String](2)
    var score = 0.0
    var categoryName = ""

    println("Processing Data")
    
    //Parse the text file line by line
    for (line <- Source.fromFile(file).getLines())
    {
      //Use a white space delimiter to get data
      dataPoint = line.split("\\s+")
      score = dataPoint(0).toDouble
      categoryName = dataPoint(1)
      
      //Search for existing category
      var cat = Categories.find { x => x.getName == categoryName }

      //If the category is not found, instantiate new and add it to the list
      if(cat == None)
      {
        Categories += new Category(categoryName, 1, 0)
        cat = Categories.find { x => x.getName == categoryName }
      }
      //Otherwise, increment count
      else
       cat.get.addCount(1)

       /*If the Heap size < top k points, enqueue current score and category,
       and increment that category's successes. This is done in order to ensure 
       that all elements of the heap WILL fit into the memory.*/
       if(mainPQ.size < draws)
       {
         //Enqueue the score and category tuple
         mainPQ.enqueue((score, categoryName))
         
         cat.get.addSuccesses(1)
       }
       /*Otherwise, once we reach the memory's capacity, we must compare the
        current element with the smallest element in the heap. If the current
        element is greater, we replace the smallest element. Thus, keeping the
        size of the node within the bounds of the system's memory.*/
       else if(score.toDouble > mainPQ.min._1)
       {
         Categories.find { x => x.getName == mainPQ.min._2 }.get.addSuccesses(-1)
         
         var newPQ = new PriorityQueue[(Double, String)]()
         
         for(i <- 0 until mainPQ.size - 1)
           newPQ.enqueue(mainPQ.dequeue()) 
  
         newPQ.enqueue((score.toDouble, categoryName))
         
         cat.get.addSuccesses(1)
         
         mainPQ = newPQ
       }
  
        popSize += 1 //Increase size of the population
     }
  }
 
      //Set the Hyper Geometric Distributions for all the categories
      def setDistrib() = {
         Categories.foreach { x => x.setHyperGeo(HyperGeometricDistribution.hyperGeoDistrib(popSize, x.getCount, draws, x.getSuccesses))}
      }
      
      //MergeSort the Hyper Geometric Distributions and return the sorted list
      def sortDistributions() : List[Category]= {
         val finalList = MergeSort.mergeSort((a: Category, b: Category) => a.getHyperGeo < b.getHyperGeo)(Categories.toList)
         
         finalList
      }
}