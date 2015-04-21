package jaccard

import org.scalatest.{FlatSpec, ShouldMatchers}

class JaccardSimilarityTest extends FlatSpec with ShouldMatchers {

  "jaccard similarity " should "be 0 when of the strings to compare is null" in {
    JaccardSimilarity.findSimilarity(null, "any") should be(0)

    JaccardSimilarity.findSimilarity("any", null) should be(0)
    JaccardSimilarity.findSimilarity(null, null) should be(0)
  }

  it should "be 1 when both of the strings to compare are empty" in {
    JaccardSimilarity.findSimilarity("", "") should be(1)
  }

  it should "be calculated for two non empty strings" in {
    JaccardSimilarity.findSimilarity("abcd", "abcd") should be(1)
    JaccardSimilarity.findSimilarity("ab", "abcd") should be(0.5)
    JaccardSimilarity.findSimilarity("abcd", "efg") should be(0)
  }

  it should "be calculated fpr input string against array of strings" in {

    val result: Array[String] = JaccardSimilarity.calculate("ab", Array("", null, "ab", "abcd", "cd", "abab"))

    result.contains("item 0, 0.0") should be(true)
    result.contains("item 1, 0.0") should be(true)
    result.contains("item 2, 1.0") should be(true)
    result.contains("item 3, 0.5") should be(true)
    result.contains("item 4, 0.0") should be(true)
    result.contains("item 5, 1.0") should be(true)

  }

}
