object Main {
  def main(args: Array[String]): Unit = {
    val i = countingSort.countingSortFromFile("./src/main/assets/test.txt")
    println(i.mkString(" "))
  }
}
