package click.rashad.template.core

import akka.actor.ActorSystem
import click.rashad.template.hello.{ GreeterGrpc, HelloReply, HelloRequest }

import scala.concurrent.{ ExecutionContext, Future }

class GreeterGrpcHandler(implicit val system: ActorSystem, val ec: ExecutionContext)
  extends GreeterGrpc.Greeter {

  override def sayHello(request: HelloRequest): Future[HelloReply] = {
    Future.successful(HelloReply(s"Hello ${request.name}!"))
  }

}
