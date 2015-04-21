package controllers

import config.PlayConfig
import play.api.Play.current
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.oauth.OAuthCalculator
import play.api.libs.ws.WS
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def twitterTransformation = Action.async { request =>

    //todo  - send transformation to spark
    //two actors one for accessing spark and another for generating statistics
    val transform: Enumeratee[Array[Byte],String] = Enumeratee.map[Array[Byte]]{
      chunk => println(new String(chunk, "UTF-8")) ; new String(chunk, "UTF-8")
    }

    WS.url("https://stream.twitter.com/1.1/statuses/sample.json")
      .sign(OAuthCalculator(PlayConfig.twitterConsumer, PlayConfig.twitterAccess))
      .withHeaders(("Content-Type", "application/json  charset=utf-8"))
      .getStream().map(result => Ok.chunked(result._2.through(transform)))


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