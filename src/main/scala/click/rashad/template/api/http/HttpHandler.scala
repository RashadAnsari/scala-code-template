package click.rashad.template.api.http

import akka.http.scaladsl.server.Route

trait HttpHandler {

  def route: Route

}
