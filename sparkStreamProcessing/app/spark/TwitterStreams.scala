package spark

import config.PlayConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j._
import twitter4j.auth.AccessToken

object TwitterStreams {

  val sparkDriverPort = 8080
  val sparkDriverHost = "localhost"

  private val sparkConf: SparkConf = new SparkConf(false) // skip loading external settings
    .setMaster("local[4]") // run locally with enough threads
    .setAppName("firstSparkApp")
    .set("spark.logConf", "true")
    .set("spark.driver.port", s"$sparkDriverPort")
    .set("spark.driver.host", s"$sparkDriverHost")
    .set("spark.akka.logLifecycleEvents", "true")


  private val twitterSampleStream: DStream[String] = {
    val twitter: TwitterStream = new TwitterStreamFactory().getInstance()
    twitter.setOAuthConsumer(PlayConfig.twitterConsumerKey, PlayConfig.twitterConsumerSecret)
    twitter.setOAuthAccessToken(new AccessToken(PlayConfig.twitterAccessToken, PlayConfig.twitterAccessTokenSecret))

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    TwitterUtils.createStream(ssc, Option(twitter.getAuthorization()), Seq.empty).map(_.getText)
  }

  def startSampleStream(transform: DStream[String] => Any) = {
    transform(twitterSampleStream)
    twitterSampleStream.context.start()
    twitterSampleStream.context.awaitTermination()
  }

}
