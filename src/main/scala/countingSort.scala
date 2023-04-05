import scala.io.Source
import scala.annotation.tailrec
import java.io.{File, PrintWriter}

object CountingSort {
  def countingSortRecursive(filename: String): Unit = {
    val input = Source.fromFile(filename)
    val lines = input.mkString.split(" ")
    val counts = scala.collection.mutable.Map[Int, Int]()
    var maxVal = Int.MinValue
    var minVal = Int.MaxValue

    // First pass to calculate counts and min/max values
    lines.foreach(line => {
      val value = line.toInt
      counts(value) = counts.getOrElse(value, 0) + 1
      maxVal = math.max(maxVal, value)
      minVal = math.min(minVal, value)
    })

    // Recursively sort the values
    def sortValues(values: List[Int], currentVal: Int, maxValue: Int): List[Int] = {
      if (currentVal > maxValue) {
        values
      } else {
        val count = counts.getOrElse(currentVal, 0)
        val nextValues = values ::: List.fill(count)(currentVal)
        sortValues(nextValues, currentVal + 1, maxValue)
      }
    }

    // Convert lines to a list of integers
    val integers = lines.map(_.toInt).toList

    // Second pass to sort the values
    val sortedValues = sortValues(List.empty[Int], minVal, maxVal)

    // Write the sorted values to a file
    val pw = new PrintWriter(new File("./src/main/assets/output.txt"))
    sortedValues.foreach(value => pw.println(value))
    pw.close()
    input.close()
  }
}
