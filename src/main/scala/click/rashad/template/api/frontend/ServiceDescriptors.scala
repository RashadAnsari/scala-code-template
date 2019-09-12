package click.rashad.template.api.frontend

import akka.actor.ActorSystem
import click.rashad.template.api.http.HttpHandler
import click.rashad.template.core.{ GreeterGrpcHandler, TemplateHttpHandler }
import io.grpc.ServerServiceDefinition

import scala.concurrent.ExecutionContext

import click.rashad.template.hello.GreeterGrpc

object ServiceDescriptors {

  def httpServices(implicit system: ActorSystem, ec: ExecutionContext): Seq[HttpHandler] = {
    Seq(new TemplateHttpHandler)
  }

  def gRPCServices(implicit system: ActorSystem, ec: ExecutionContext): Seq[ServerServiceDefinition] = {
    Seq(GreeterGrpc.bindService(new GreeterGrpcHandler, ec))
  }

}
