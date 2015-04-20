package config

import org.pac4j.core.client.Clients
import org.pac4j.oauth.client.TwitterClient
import org.pac4j.play.Config
import play.api.Play
import play.api.libs.oauth.{RequestToken, ConsumerKey}


object PlayConfig {
  val config = Play.current.configuration

  val twitterConsumerKey = config.getString("twitter.consumer.key").getOrElse("keyNotDefined")

  val twitterConsumerSecret = config.getString("twitter.consumer.secret").getOrElse("secretNotDefined")

  val twitterAccessToken = config.getString("twitter.access.token").getOrElse("accessTokenNotDefined")

  val twitterAccessTokenSecret = config.getString("twitter.access.token_secret").getOrElse("accessTokenSecretNotDefined")

  val twitterClient = new TwitterClient(twitterConsumerKey, twitterConsumerSecret)

  val twitterConsumer = ConsumerKey(twitterConsumerKey, twitterConsumerSecret)

  val twitterAccess = RequestToken(twitterAccessToken, twitterAccessTokenSecret)


  def setUpTwitterAuth(): Unit = {
    val clients = new Clients("http://localhost:9000/callback", twitterClient)
    Config.setClients(clients)
  }
}
