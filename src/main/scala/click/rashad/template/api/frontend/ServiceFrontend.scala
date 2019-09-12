package click.rashad.template.api.frontend

import akka.actor.ActorSystem
import click.rashad.template.api.grpc.GrpcFrontend
import click.rashad.template.api.http._
import com.typesafe.config.Config
import io.grpc.ServerServiceDefinition

import scala.collection.JavaConverters._

object EndpointTypes {

  sealed trait EndpointType

  case object Http extends EndpointType

  case object Grpc extends EndpointType

}

object Endpoint {

  import EndpointTypes._

  case class Endpoint(
    typ:  EndpointType,
    host: String,
    port: Int
  )

  def getEndpoint(config: Config): Endpoint = {
    Endpoint(
      getEndpointType(
        config.getString("type")
      ),
      config.getString("host"),
      config.getInt("port")
    )
  }

  def getEndpointType(typ: String): EndpointType = {
    typ match {
      case "http" ⇒ EndpointTypes.Http
      case "grpc" ⇒ EndpointTypes.Grpc
    }
  }

}

object Frontend {

  import Endpoint._

  def start(
    httpServices: Seq[HttpHandler],
    grpcServices: Seq[ServerServiceDefinition]
  )(implicit config: Config, system: ActorSystem): Unit = {
    config.getConfigList("endpoints").asScala
      .map(getEndpoint) foreach (startEndpoint(_, httpServices, grpcServices))
  }

  private def startEndpoint(
    endpoint:     Endpoint,
    httpServices: Seq[HttpHandler],
    grpcServices: Seq[ServerServiceDefinition]
  )(implicit system: ActorSystem): Unit = {
    endpoint.typ match {
      case EndpointTypes.Http ⇒
        HttpFrontend.start(endpoint.host, endpoint.port, httpServices)
      case EndpointTypes.Grpc ⇒
        GrpcFrontend.register(grpcServices)
        GrpcFrontend.start(endpoint.host, endpoint.port)
    }
  }

}
