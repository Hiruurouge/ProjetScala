import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.ArrayBuffer

object RadixCountingSortSpark {

  def countingSort(inputRdd: org.apache.spark.rdd.RDD[Int], exp: Int): org.apache.spark.rdd.RDD[Int] = {
    val base = 10
    val count = inputRdd.map(number => ((number / exp) % base, 1)).reduceByKey(_ + _).collect().sorted
    val prefixSum = count.scanLeft((0, 0)) { case ((_, acc), (k, v)) => (k, acc + v) }.drop(1)

    val outputRdd = inputRdd.map(number => {
      val index = (number / exp) % base
      val position = prefixSum.find(_._1 == index).map(_._2).getOrElse(0)
      (position, number)
    })

    outputRdd.sortByKey().values
  }

  def radixSort(inputFileName: String): Unit = {
    val outputFileName = "spark_output.txt"
    val conf = new SparkConf().setAppName("RadixCountingSortSpark").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val inputRdd = sc.textFile(inputFileName).flatMap(_.split(" ")).map(_.toInt)
    val max = 500
    val maxExp = 3

    var exp = 1
    var sortedRdd = inputRdd

    for (_ <- 1 to maxExp) {
      sortedRdd = countingSort(sortedRdd, exp)
      exp *= 10
    }

    val outputData = sortedRdd.collect().mkString(" ")
    import java.nio.file.{Files, Paths}
    Files.write(Paths.get(outputFileName), outputData.getBytes)
    sc.stop()
  }
}
