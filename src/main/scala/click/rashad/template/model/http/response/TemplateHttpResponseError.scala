package click.rashad.template.model.http.response

import spray.json._

// fixme: template
case class TemplateHttpResponseError(code: Int, message: String)

// fixme: template
object TemplateHttpResponseErrorJsonProtocol extends DefaultJsonProtocol {
  // fixme: template
  implicit val TemplateHttpResponseErrorJsonProtocol: RootJsonFormat[TemplateHttpResponseError] = jsonFormat2(TemplateHttpResponseError)
}
