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

    val outputFile = new File("./src/main/assets/output.txt")
    outputFile.delete()
    val pw = new PrintWriter(outputFile)

    // Recursively sort the values
    @tailrec
    def sortValues(currentVal: Int, maxValue: Int): Unit = {
      if (currentVal <= maxValue) {
        val count = counts.getOrElse(currentVal, 0)
        for (elem <- List.fill(count)(currentVal)) {
          pw.println(elem)
        }
        sortValues(currentVal + 1, maxValue)
      }
    }

    sortValues(minVal, maxVal)
    pw.close()
    input.close()
  }
  def radixSort(filename: String): Unit = {
    val numDigits = 10
    (0 until numDigits).foreach(i => {
      countingSortRecursive(filename)
      val input = Source.fromFile("./src/main/assets/output.txt")
      val output = new PrintWriter(new File("fin.txt"))
      input.getLines.foreach(line => {
        val value = line.toInt
        val padded = "%010d".format(value)
        output.println(padded)
      })
      output.close()
      input.close()
    })
  }
}
