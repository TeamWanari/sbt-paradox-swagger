package com.wanari.paradox.swagger

import com.lightbend.paradox.markdown.Writer
import com.lightbend.paradox.sbt.ParadoxPlugin
import com.lightbend.paradox.sbt.ParadoxPlugin.autoImport.paradoxDirectives
import sbt.PluginTrigger.AllRequirements
import sbt._

object SwaggerParadoxPlugin extends AutoPlugin {

  override def requires: Plugins = ParadoxPlugin

  override def trigger: PluginTrigger = AllRequirements

  override def projectSettings: Seq[Setting[_]] =
    swaggerParadoxSettings(Compile)

  def swaggerParadoxGlobalSettings: Seq[Setting[_]] = Seq(
    paradoxDirectives ++= Def.taskDyn {
      Def.task {
        Seq(
          { _: Writer.Context ⇒
            new SwaggerParadoxDirective()
          }
        )
      }
    }.value
  )

  def swaggerParadoxSettings(config: Configuration): Seq[Setting[_]] =
    swaggerParadoxGlobalSettings ++ inConfig(config)(
      Seq(
        // scoped settings here
      ))
}
