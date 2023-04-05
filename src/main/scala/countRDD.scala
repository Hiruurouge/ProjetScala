import org.apache.spark.{SparkConf, SparkContext}

import java.io.{File, PrintWriter}

object countRDD {

  def countingSortSpark(filename: String, writeToFile: String => Unit, sc: SparkContext): Unit = {
    val rdd = sc.textFile(filename).flatMap(_.split(" ")).map(_.toInt)

    val countsRdd = rdd.map(elem => (elem, 1)).reduceByKey(_ + _).sortByKey()

    val sortedArr = countsRdd.flatMap { case (elem, count) => Array.fill(count)(elem) }.collect()

    writeToFile(sortedArr.mkString(" "))
  }
  def writeToFile(filename: String, content: String): Unit = {
    val pw = new PrintWriter(new File(filename))
    pw.println(content)
    pw.close()
  }
  def radixSort(filename: String, numDigits: Int, sc: SparkContext): Unit = {

    (0 until numDigits).foreach(i => {
      val rdd1 = sc.textFile(filename).flatMap(_.split(" ")).map(_.toInt)
      val countsRdd = rdd1.map(elem => (elem, 1)).reduceByKey(_ + _).sortByKey()
      val sortedArr = countsRdd.flatMap { case (elem, count) => Array.fill(count)(elem) }.collect()
      writeToFile("./src/main/assets/output.txt", sortedArr.mkString(" "))
      val rdd = sc.textFile("./src/main/assets/output.txt")
      val output = new PrintWriter(new File("fin.txt"))
      rdd.mapPartitions { partition =>
        val output = new PrintWriter(new File("fin.txt"))
        partition.flatMap(_.split(" "))
          .map(_.toInt)
          .map(elem => ("%0" + numDigits + "d").format(elem))
          .foreach(output.println)
        output.close()
        Iterator.empty
      }.count()
    })
  }
}