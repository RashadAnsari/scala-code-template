package click.rashad.template.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import click.rashad.template.core.TemplateHttpHandler
import click.rashad.template.model.http.response.TemplateHttpResponseJsonProtocol._
import click.rashad.template.model.http.response._
import spray.json._

import scala.util._

trait HttpHandlerHelper extends SprayJsonSupport {
  this: TemplateHttpHandler ⇒

  import click.rashad.template.model.http.response.TemplateHttpResponseErrorJsonProtocol._

  private val InternalServerError = TemplateHttpResponseError(500, StatusCodes.InternalServerError.defaultMessage).toJson.toString
  private val InternalErrorResponse = HttpResponse(
    status = StatusCodes.InternalServerError,
    entity = HttpEntity(ContentTypes.`application/json`, InternalServerError)
  )

  def generateHttpResponse(username: String): PartialFunction[Try[Either[Throwable, TemplateHttpResponse]], StandardRoute] = {
    case Success(result) ⇒
      result match {
        case Right(resp) ⇒ httpResponseWithOkStatus(resp.toJson.toString)
        case Left(ex) ⇒
          log.error(ex, "Internal server error for user {}", username)
          complete(InternalErrorResponse)
      }
    case Failure(ex) ⇒
      log.error(ex, "Internal server error for user {}", username)
      complete(InternalErrorResponse)
  }

  private def httpResponseWithOkStatus(json: String): StandardRoute = {
    complete {
      HttpResponse(
        status = StatusCodes.OK,
        entity = HttpEntity(ContentTypes.`application/json`, json)
      )
    }
  }

  implicit def rejectionHandler =
    RejectionHandler.default
      .mapRejectionResponse {
        case res @ HttpResponse(_, _, _: HttpEntity.Strict, _) ⇒
          val rejectResponse = TemplateHttpResponseError(res.status.intValue(), res.status.defaultMessage()).toJson.toString
          res.copy(entity = HttpEntity(ContentTypes.`application/json`, rejectResponse))
        case x ⇒ x
      }

}