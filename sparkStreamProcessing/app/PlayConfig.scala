package config

import play.api.Play
import play.api.libs.oauth.{ConsumerKey, RequestToken}


object PlayConfig {
  val config = Play.current.configuration

  val twitterConsumerKey = config.getString("twitter.consumer.key").getOrElse("keyNotDefined")

  val twitterConsumerSecret = config.getString("twitter.consumer.secret").getOrElse("secretNotDefined")

  val twitterAccessToken = config.getString("twitter.access.token").getOrElse("accessTokenNotDefined")

  val twitterAccessTokenSecret = config.getString("twitter.access.token_secret").getOrElse("accessTokenSecretNotDefined")

  val twitterConsumer = ConsumerKey(twitterConsumerKey, twitterConsumerSecret)

  val twitterAccess = RequestToken(twitterAccessToken, twitterAccessTokenSecret)

}
