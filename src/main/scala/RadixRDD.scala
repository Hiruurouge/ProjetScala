import org.apache.spark.{SparkConf, SparkContext}
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.math._

object RadixRDD {
  def radixSort(inputFilename: String, maximum: Int): Unit = {
    val outputFilename = "outputDigit0.txt"
    val maxDigit = if (maximum == 0) 1 else floor(log10(abs(maximum)) + 1).toInt

    @tailrec
    def countingSort(inputFilename: String, outputFilename: String, digit: Int): Unit = {
      if (digit != maxDigit) {
        val conf = new SparkConf().setAppName("Radix Counting Sort")
        val sc = new SparkContext(conf)
        val base = 10
        val exp = pow(base, digit).toInt

        val numbers = sc.textFile(inputFilename)
          .flatMap(_.split(' '))
          .filter(_ != "")
          .map(_.toInt)
          .cache()

        val count = numbers
          .map(number => ((number / exp) % base, 1))
          .reduceByKey(_ + _)
          .collect()
          .sortBy(_._1)
          .map(_._2)

        for (i <- 1 until count.length) {
          count(i) += count(i - 1)
        }

        val outputArray = new Array[Int](numbers.count.toInt)
        numbers.collect().reverse.foreach { number =>
          val index = (number / exp) % base
          count(index) -= 1
          outputArray(count(index)) = number
        }

        val printWriter = new java.io.PrintWriter(new java.io.FileWriter(outputFilename))

        outputArray.foreach { number =>
          printWriter.print(s"$number ")
        }
        printWriter.flush()
        printWriter.close()

        sc.stop()

        countingSort(outputFilename, s"outputDigit${digit + 1}.txt", digit + 1)
      }
    }

    countingSort(inputFilename, outputFilename, 0)
  }
}
