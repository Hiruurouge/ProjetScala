import org.apache.spark.{SparkConf, SparkContext}
import java.io.{File, PrintWriter,FileOutputStream}
object Main {
  def main(args: Array[String]): Unit = {

    //RadixCountingSort.radixSort("./src/main/assets/10Mega.txt")
    RadixRDD.radixSort("./src/main/assets/test.txt",500)
    //println(args.length)
  }
}
/*
 val startTime = currentTimeMillis()
  val result = block
  val endTime = currentTimeMillis()
  val writer = new PrintWriter("temps_execution.txt")
  writer.write(s"$label : ${endTime - startTime} ms\n")
  writer.close()
*/