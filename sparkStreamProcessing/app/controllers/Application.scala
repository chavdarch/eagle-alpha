package controllers

import config.PlayConfig
import org.pac4j.play.scala.ScalaController
import play.api.Play.current
import play.api.libs.iteratee.Enumerator
import play.api.libs.oauth.OAuthCalculator
import play.api.libs.ws.WS
import play.api.mvc.Action

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends ScalaController {

  def twitterTransformation = RequiresAuthentication("TwitterClient") { profile =>
    Action.async { request =>

     WS.url("https://stream.twitter.com/1.1/statuses/sample.json")
       .sign(OAuthCalculator(PlayConfig.twitterConsumer, PlayConfig.twitterAccess))
        .getStream().map(result => Ok.chunked(result._2))


    }
  }

  def twitterTransformationStatistics = Action {
    val response = WS.url("https://stream.twitter.com/1.1/statuses/sample.json")
      .withHeaders("oauth_consumer_key" -> "eCKkJysrFHNkRkAPOztEmxOnX")
      .withHeaders("oauth_nonce" -> "a99f43b584fc67320718ab513947ff1e")
      .withHeaders("oauth_signature" -> "5QbJyxR%2BdTltQNDfC5JdzfV%2FO9M%3D")
      .withHeaders("oauth_signature_method" -> "HMAC-SHA1")
      .withHeaders("oauth_timestamp" -> "1429564850")
      .withHeaders("oauth_token" -> "105247934-cDaTO27WqEQhimNTc6aX29OJlAblIBmZQMxxBbSR")
      .withHeaders("oauth_version" -> "1.0").getStream()

    Ok.chunked {
      Enumerator.flatten(response.map(_._2))
    }

  }
  
}