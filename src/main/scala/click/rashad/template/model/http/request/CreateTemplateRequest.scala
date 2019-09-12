package click.rashad.template.model.http.request

import spray.json._

case class CreateTemplateRequest(templateName: String)

object CreateTemplateRequestJsonProtocol extends DefaultJsonProtocol {
  implicit val CreateTemplateRequestJsonProtocol: RootJsonFormat[CreateTemplateRequest] = jsonFormat1(CreateTemplateRequest)
}