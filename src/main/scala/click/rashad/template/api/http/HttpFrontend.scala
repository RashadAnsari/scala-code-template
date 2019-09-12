package click.rashad.template.api.http

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.settings.ServerSettings
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext
import scala.util._

object HttpFrontend {

  def start(host: String, port: Int, services: Seq[HttpHandler])(implicit system: ActorSystem): Unit = {
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val log = Logging(system, getClass)

    val routes: Seq[Route] = services.map(_.route)
    val mainRoute: Route = routes.foldLeft(reject: Route)(_ ~ _)

    Http().bindAndHandle(
      mainRoute,
      host,
      port,
      settings = ServerSettings(ConfigFactory.load)
    ) onComplete {
        case Success(binding) ⇒
          log.info(s"Server online at http://{}/", binding.localAddress)
        case Failure(cause) ⇒
          log.error(cause, "Failed to start HTTP server")
      }
  }

}
