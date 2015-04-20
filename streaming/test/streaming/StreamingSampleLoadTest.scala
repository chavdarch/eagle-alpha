package streaming

import org.scalatestplus.play._
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WS, WSResponseHeaders}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global

trait StreamingFixture extends PlaySpec with OneServerPerSuite {

  def getSampleStream(port: Int): Enumerator[Array[Byte]] = {
    val (headers, resultStream): (WSResponseHeaders, Enumerator[Array[Byte]]) = await(
      WS.url(s"http://localhost:$port/streaming").getStream()
    )
    resultStream
  }
}

class StreamingSampleLoadTest extends StreamingFixture {

  implicit override lazy val port = 7702
  implicit override lazy val app: FakeApplication = FakeApplication()

  " sample streaming for 1000 clients" in {
    val (enumerator, channel) = Concurrent.broadcast[JsValue]

    val freeMemBefore: Long = Runtime.getRuntime.freeMemory()

    (1 to 1000).map(_ => getSampleStream(port)).foreach { stream =>
      stream.run(Iteratee.ignore)
    }

    println("Memory in MB used for 1000 clients:" + (freeMemBefore - Runtime.getRuntime.freeMemory()) / 1000000)


    Thread.sleep(1000)
  }
}


class StreamingSampleTest extends StreamingFixture {
  implicit override lazy val port = 7703
  implicit override lazy val app: FakeApplication = FakeApplication()

  " sample streaming for 1 second" in {
    val consume: Iteratee[JsValue, Unit] = Iteratee.foreach[JsValue](e => println(Json.prettyPrint(e)))

    val r = getSampleStream(port).map(Json.parse).apply(consume)

    Thread.sleep(1000)

  }

}
