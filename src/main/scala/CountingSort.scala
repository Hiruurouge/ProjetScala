import java.io._

object CountingSort {
  def countingSort(inputFilename: String, outputFilename: String): Unit = {
    val input = new BufferedReader(new FileReader(inputFilename))
    val output = new BufferedWriter(new FileWriter(outputFilename))
    val counts = new Array[Int](501)

    // First pass to calculate counts
    var line = input.readLine()
    while (line != null) {
      val values = line.split(" ")
      for (value <- values) {
        counts(value.toInt) += 1
      }
      line = input.readLine()
    }

    // Write sorted values to output file
    for (i <- 0 until counts.length) {
      for (j <- 1 to counts(i)) {
        output.write(s"$i ")
      }
    }

    input.close()
    output.close()
  }
}
