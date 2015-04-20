package controllers

import streaming.Generator
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.Enumerator
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object Application extends Controller {
  //stream random sample once every 100 millisec(~ 10 times per second)
  def streaming = Action {
    Ok.chunked(Enumerator.generateM {
      Promise.timeout(Some(Generator.generateStreamingJson), 100 millisecond)
    })
  }
}