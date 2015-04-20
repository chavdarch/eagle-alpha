package streaming

import java.util.UUID

import play.api.libs.json.{Json, JsValue, Writes}

import scala.util.Random


case class StreamingSample(id: UUID, name: String)

object Generator {
  implicit val streamingWrites = new Writes[StreamingSample] {
    def writes(sample: StreamingSample): JsValue = {
      Json.obj(
        "id" -> sample.id,
        "name" -> sample.name
      )
    }
  }

  def generateStreamingJson: String = {
    val sample = StreamingSample(UUID.randomUUID(), Random.nextString(10))
    Json.prettyPrint(Json.toJson(sample))
  }
}

