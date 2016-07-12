package MergeSort

object MergeSort {
 
 def mergeSort[T] (lessOp: (T, T) => Boolean)(list: List[T]): List[T] = {
   
    //Get the middle of the list
    val listLen = list.length / 2
    
    //Base case, return the list
    if (listLen == 0) 
      list
    else {
      @scala.annotation.tailrec
      def merge(leftSide: List[T], rightSide: List[T], newList: List[T] = List()): List[T] = {
        (leftSide, rightSide) match {
          //If the left list is empty, return a new list from the elements of new list and right list
          case(Nil, _) => newList ++ rightSide
          //If the Right list is empty, return a new list from the elements of new list and left list
          case(_, Nil) => newList ++ leftSide
          //Compare the top of the left and right list
          case(leftTop :: leftRest, rightTop :: rightRest) =>
            //If rightTop < leftTop, return a new list with the leftTop appended
            if (lessOp(rightTop,leftTop)) 
              merge(leftRest, rightSide, newList:+leftTop)
            //Vice Versa
            else 
              merge(leftSide, rightRest, newList:+rightTop)
        }
      }
      
      //Split the list based on the remaining size
      val (left, right) = list.splitAt(listLen)
     
      //Recursively merge to get the separated lists back together
      merge(mergeSort(lessOp)(left), mergeSort(lessOp)(right))
    }
  }
}