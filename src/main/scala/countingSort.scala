import scala.io.Source
import scala.annotation.tailrec
object countingSort {
  def countingSortRecursive(arr: Array[Int]): Array[Int] = {
    val maxVal = arr.max;
  @tailrec
  def countingSortRec(arr: Array[Int], counts: Array[Int], index: Int): Array[Int] = {
    if (index < arr.length) {
      counts(arr(index)) += 1
      countingSortRec(arr, counts, index + 1)
    } else {
      var i = 0
      var j = 0
      while (i <= maxVal) {
        while (counts(i) > 0) {
          arr(j) = i
          j += 1
          counts(i) -= 1
        }
        i += 1
      }
      arr
    }
  }

  val counts = Array.fill(maxVal + 1)(0)
  countingSortRec(arr, counts, 0)
}

  def countingSortFromFile(filename: String): Array[Int] = {
    val lines = Source.fromFile(filename).getLines()
    val numbers = lines.flatMap(_.split(" ")).map(_.toInt).toList
    countingSortRecursive(numbers.toArray)
  }

}
