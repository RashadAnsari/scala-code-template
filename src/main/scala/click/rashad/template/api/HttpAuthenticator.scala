package click.rashad.template.api

import akka.http.scaladsl.server.directives.Credentials
import click.rashad.template.core.TemplateHttpHandler

trait HttpAuthenticator {
  this: TemplateHttpHandler ⇒

  private lazy val userPassMap: Map[String, String] = {
    import scala.collection.JavaConverters._

    val confObj = config.getConfig("services.basic-auth")
    confObj.entrySet().asScala.toList.map(entry ⇒ entry.getKey → entry.getValue.unwrapped().toString).toMap
  }

  def basicAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p @ Credentials.Provided(username) ⇒
        val password = userPassMap.get(username)
        if (password.isDefined && p.verify(password.get)) Some(username) else None
      case _ ⇒
        None
    }

}
