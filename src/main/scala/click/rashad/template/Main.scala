package click.rashad.template

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.management.scaladsl.AkkaManagement
import click.rashad.commons.config.{ AppType, ScalaConfig }
import click.rashad.template.api.frontend.{ Frontend, ServiceDescriptors }

object Main extends App {

  private implicit val config = ScalaConfig.load(AppType.Server)

  // fixme: template
  private implicit val system = ActorSystem("scala-code-template", config)

  import system.dispatcher

  if (config.getList("akka.cluster.seed-nodes").isEmpty) {
    system.log.info("Going to a single-node cluster mode")
    Cluster(system).join(Cluster(system).selfAddress)
  }

  AkkaManagement(system).start()

  Frontend.start(ServiceDescriptors.httpServices, ServiceDescriptors.gRPCServices)

}
