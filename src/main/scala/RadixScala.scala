import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.collection.mutable.ArrayBuffer

object RadixCountingSort {

  def countingSort(inputFileName: String, outputFileName: String, exp: Int): Unit = {
    val base = 10
    val count = new Array[Int](base)

    val bufferedReader1 = new BufferedReader(new FileReader(inputFileName))
    val numbers = ArrayBuffer[Int]()
    Iterator.continually(bufferedReader1.readLine()).takeWhile(_ != null).flatMap(_.split(' ')).map(_.toInt).foreach { number =>
      count((number / exp) % base) += 1
      numbers += number
    }
    bufferedReader1.close()

    for (i <- 1 until base) {
      count(i) += count(i - 1)
    }

    val outputArray = new Array[Int](numbers.length)
    for (i <- numbers.length - 1 to 0 by -1) {
      val number = numbers(i)
      val index = (number / exp) % base
      count(index) -= 1
      outputArray(count(index)) = number
    }

    val printWriter = new PrintWriter(new FileWriter(outputFileName))
    outputArray.foreach { number =>
      printWriter.print(s"$number ")
    }
    printWriter.flush()
    printWriter.close()
  }

  def radixSort(inputFileName: String): Unit = {
    val outputFileName = "src.output.txt"
    val maxExp = 3

    var exp = 1
    var currentInputFileName = inputFileName
    var currentOutputFileName = outputFileName

    for (i <- 1 to maxExp) {
      if (i != maxExp) {
        currentOutputFileName = s"output_$i.txt"
      }
      countingSort(currentInputFileName, currentOutputFileName, exp)
      exp *= 10
      currentInputFileName = currentOutputFileName
    }
  }
}