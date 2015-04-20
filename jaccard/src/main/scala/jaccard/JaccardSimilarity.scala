package jaccard

private[jaccard] object JaccardSimilarityApp extends App {
  val testArray = Array("", null, "ab", "abcd", "cd")
  println(
    s"""
      Running jacard similarity test for: "ab" vs ${testArray.toList}
    """.stripMargin)

 JaccardSimilarity.calculate("ab", testArray).foreach(println)
}

object JaccardSimilarity {

  /**
   * Find the Jaccard Similarity of two strings
   * @param stringOne the first string to compare
   * @param stringTwo the second string to compare
   * @return the Jaccard Similarity (intersect(A,B) / union(A, B))
   */
  def findSimilarity(stringOne: String, stringTwo: String): Double = {
    if (stringOne == null || stringTwo == null) {
      0
    } else if (stringOne.isEmpty && stringTwo.isEmpty) {
      1
    } else {
      val stringOneSet = stringOne.toCharArray.toSet
      val stringTwoSet = stringTwo.toCharArray.toSet

      stringOneSet.intersect(stringTwoSet).size.toDouble /
        stringOneSet.union(stringTwoSet).size

    }

  }

  // {item index, score}
  def calculate(input: String, terms: Array[String]): Array[String] = {
    terms.zipWithIndex.map { term: (String, Int) =>
      s"item ${term._2}, ${findSimilarity(input, term._1)}"
    }
  }
}

