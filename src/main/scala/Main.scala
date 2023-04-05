object Main {
  def main(args: Array[String]): Unit = {
    //CountRDD.countingSortSpark("./src/main/assets/test.txt")
    CountingSort.countingSortRecursive("./src/main/assets/test.txt")
  }
}
