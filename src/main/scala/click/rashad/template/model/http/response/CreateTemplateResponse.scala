package click.rashad.template.model.http.response

import spray.json._

case class CreateTemplateResponse(id: Int, templateName: String) extends TemplateHttpResponse

object CreateTemplateResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val CreateTemplateResponseJsonProtocol: RootJsonFormat[CreateTemplateResponse] = jsonFormat2(CreateTemplateResponse)
}