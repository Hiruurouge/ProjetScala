import org.apache.spark.{SparkConf, SparkContext}

object countRDD {
  def countingSortSpark(filename: String): Unit = {
    val conf = new SparkConf().setAppName("Counting Sort with Spark").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(filename).flatMap(_.split(" ")).map(_.toInt)

    val countsRdd = rdd.map(elem => (elem, 1)).reduceByKey(_ + _).sortByKey()

    val sortedArr = countsRdd.flatMap { case (elem, count) => Array.fill(count)(elem) }

    sortedArr.saveAsTextFile("./src/main/assets/output.txt")

    sc.stop()
  }
}
