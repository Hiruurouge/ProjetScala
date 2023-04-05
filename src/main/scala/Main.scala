object Main {
  def main(args: Array[String]): Unit = {
    countRDD.countingSortSpark("./src/main/assets/test.txt")
    //CountingSort.countingSortRecursive("./src/main/assets/test.txt")
    //println(util.Properties.versionString)
  }
}
