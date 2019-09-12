package click.rashad.template.core

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import click.rashad.template.api.http.HttpHandler
import click.rashad.template.api.{ HttpAuthenticator, HttpHandlerHelper }
import click.rashad.template.model.http.request.CreateTemplateRequest
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext

// fixme: template
class TemplateHttpHandler(implicit val system: ActorSystem, val ec: ExecutionContext)
  extends HttpHandler with HttpHandlerHelper with HttpAuthenticator with TemplateHttpHandlerImpl {

  protected val config: Config = system.settings.config
  protected val log: LoggingAdapter = Logging(system, getClass)

  override def route: Route = Route.seal {
    extractClientIP { ip ⇒
      log.info("Request received from ip {}", ip)
      path("api" / "v1" / "template") {
        put {
          authenticateBasic(realm = "secure site", credentials ⇒ basicAuthenticator(credentials)) { username ⇒
            import click.rashad.template.model.http.request.CreateTemplateRequestJsonProtocol._
            entity(as[CreateTemplateRequest]) { request ⇒
              onComplete(createTemplate(request)) {
                generateHttpResponse(username)
              }
            }
          }
        }
      }
    }
  }

}
