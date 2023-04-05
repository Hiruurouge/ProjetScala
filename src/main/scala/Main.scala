import countRDD.writeToFile
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  def main(args: Array[String]): Unit = {
    //countRDD.countingSortSpark("./src/main/assets/10Mega.txt")
    //CountingSort.countingSortRecursive("./src/main/assets/test.txt")
    //println(util.Properties.versionString)
    //CountingSort.radixSort("./src/main/assets/test.txt")
    //val conf = new SparkConf().setAppName("Radix Sort with Spark").setMaster("local[*]")
    //val sc = new SparkContext(conf)
    //countRDD.radixSort("./src/main/assets/test.txt",10,sc)
    //countRDD.countingSortSpark("./src/main/assets/test.txt",content => countRDD.writeToFile("./src/main/assets/output.txt", content),sc)
    //sc.stop()
    val conf = new SparkConf().setAppName("Radix Sort with Spark").setMaster("local[*]")
    val sc = new SparkContext(conf)
    countRDD.countingSortSpark("./src/main/assets/test.txt",content => countRDD.writeToFile("./src/main/assets/output.txt", content),sc)
    //countRDD.radixSort("./src/main/assets/test.txt",10,sc)
    sc.stop()
  }
}
