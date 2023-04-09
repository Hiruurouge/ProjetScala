import org.apache.spark.{SparkConf, SparkContext}

import java.io.{File, PrintWriter}

object countRDD {

  def countingSortSpark(filename: String,outputFileName: String): Unit = {
    val conf = new SparkConf().setAppName(" Sort with Spark").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile(filename).flatMap(_.split(" ")).map(_.toInt)

    val countsRdd = rdd.map(elem => (elem, 1)).reduceByKey(_ + _).sortByKey()

    val sortedArr = countsRdd.flatMap { case (elem, count) => Array.fill(count)(elem) }.collect()

    val pw = new PrintWriter(new File(outputFileName))
    pw.println(sortedArr.mkString(" "))
    pw.close()  
    sc.stop()
  }
}