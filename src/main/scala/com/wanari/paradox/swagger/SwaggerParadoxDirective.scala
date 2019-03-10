package com.wanari.paradox.swagger

import java.util.UUID

import com.lightbend.paradox.markdown.{InlineDirective, LeafBlockDirective}
import org.pegdown.Printer
import org.pegdown.ast.DirectiveNode.Source
import org.pegdown.ast.{DirectiveNode, Visitor}
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.nio.file.Files
import java.nio.file.Paths

import org.pegdown.ast.DirectiveNode.Format.ContainerBlock

class SwaggerParadoxDirective extends InlineDirective("swagger") {

  override def render(node: DirectiveNode,
                      visitor: Visitor,
                      printer: Printer): Unit = {

    val id = s"swagger-ui-${UUID.randomUUID.toString.take(8)}"

    val fromFile = node.source match {
      case Source.Empty     => node.label
      case x: Source.Direct => x.value
      case x: Source.Ref    => x.value
    }
    val toFile = s"target/paradox/site/main/swagger/${node.label}"
    Files.createDirectories(Paths.get(toFile))
    Files.copy(Paths.get(fromFile), Paths.get(toFile), REPLACE_EXISTING)

    printer
      .print("</p>") //close the auto generated <p>
      .println()

    printer
      .print("""<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swagger-ui-dist@3.17.0/swagger-ui.css">""")
      .println()
      .print("""<script src="https://unpkg.com/swagger-ui-dist@3/swagger-ui-bundle.js"></script>""")
      .println()
      .print(s"""<script>
           |  window.onload = function() {
           |    var ui = SwaggerUIBundle({
           |      url: `swagger/${node.label}`,
           |      dom_id: '#$id',
           |      presets: [
           |        SwaggerUIBundle.presets.apis,
           |        SwaggerUIBundle.SwaggerUIStandalonePreset
           |      ]
           |    });
           |  }
           |</script>""".stripMargin)
      .println()
      .print(s"""<div id="$id"></div>""")
      .println()

    printer
      .print("<p>") //open the auto generated </p>
  }
}
