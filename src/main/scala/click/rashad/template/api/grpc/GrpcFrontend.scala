package click.rashad.template.api.grpc

import java.net.InetSocketAddress

import io.grpc._
import io.grpc.netty.NettyServerBuilder
import org.slf4j.LoggerFactory

object GrpcFrontend {
  self ⇒

  private val log = LoggerFactory.getLogger(getClass)
  private var serverBuilder: ServerBuilder[_] = _
  private var server: Server = _
  private val services = scala.collection.mutable.ArrayBuffer.empty[ServerServiceDefinition]

  def start(host: String, port: Int): Unit = {
    serverBuilder = NettyServerBuilder.forAddress(new InetSocketAddress(host, port))

    services.foreach(serverBuilder.addService(_))
    log.info("Services added")

    server = serverBuilder.build.start
    log.info("Grpc server started, listening on {}:{}", host, port)

    sys.addShutdownHook {
      log.warn("Shutting down gRPC server since JVM is shutting down")
      self.stop()
      log.warn("Server shut down")
    }
  }

  def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  def register(grpcServices: Seq[ServerServiceDefinition]): Unit = {
    if (server != null) throw new RuntimeException("Server was initialized")
    services ++= grpcServices
  }

}
