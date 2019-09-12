package click.rashad.template.persist

import click.rashad.template.model.repo.Template
import com.github.tminglei.slickpg.ExPostgresProfile.api._
import slick.dbio.Effect
import slick.lifted.Tag
import slick.sql.FixedSqlAction

class TemplateTable(tag: Tag) extends Table[Template](tag, "templates") {

  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name")

  def * = (id, name) <> (Template.tupled, Template.unapply)

}

object TemplateRepo {

  private val templates = TableQuery[TemplateTable]

  def create(id: Int, name: String): FixedSqlAction[Int, NoStream, Effect.Write] =
    templates += Template(id, name)

}

