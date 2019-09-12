package click.rashad.template.model.http.response

import spray.json._

// fixme: template
trait TemplateHttpResponse

// fixme: template
object TemplateHttpResponseJsonProtocol extends DefaultJsonProtocol {

  import CreateTemplateResponseJsonProtocol._

  // fixme: template
  implicit object TemplateHttpResponseJsonFormat extends RootJsonFormat[TemplateHttpResponse] {
    override def read(json: JsValue): TemplateHttpResponse = ???

    override def write(obj: TemplateHttpResponse): JsValue = {
      obj match {
        case r: CreateTemplateResponse ⇒ r.toJson
      }
    }
  }

}
