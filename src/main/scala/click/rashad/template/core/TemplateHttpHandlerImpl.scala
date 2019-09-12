package click.rashad.template.core

import click.rashad.commons.concurrent.FutureResult
import click.rashad.commons.persist.PostgresDbExtension
import click.rashad.template.model.http.request.CreateTemplateRequest
import click.rashad.template.model.http.response.CreateTemplateResponse
import click.rashad.template.persist.TemplateRepo

import scala.concurrent.Future
import scala.util._

trait TemplateHttpHandlerImpl extends FutureResult[Throwable] {
  this: TemplateHttpHandler ⇒

  private val db = PostgresDbExtension(system).db

  def createTemplate(request: CreateTemplateRequest): Future[Either[Throwable, CreateTemplateResponse]] = {
    val id = Random.nextInt()
    val name = request.templateName
    fromFuture(db.run(TemplateRepo.create(id, name)))
      .map(_ ⇒ CreateTemplateResponse(id, name)).value
  }

}
