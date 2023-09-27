import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import fs2.*
import fs2.concurrent.*
import fs2.dom.*

import frontroute.*
import frontroute.given

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("styles/index.css", JSImport.Default)
object IndexCss extends js.Object

object App {
  val indexCss: IndexCss.type = IndexCss

  val tabs = List(
    "introduction" -> "Introduction",
    "knowledge" -> "Knowledge",
    "Message" -> "Message"
  )

  val app: Resource[IO, HtmlDivElement[IO]] =
    SignallingRef[IO].of("world").toResource.flatMap { name =>
      div(
        label("Your name: "),
        input.withSelf { self =>
          (
            placeholder := "Enter your name here",
            // here, input events are run through the given Pipe
            // this starts background fibers within the lifecycle of the <input> element
            onInput --> (_.foreach(_ => self.value.get.flatMap(name.set)))
          )
        },
        span(" Hello1, ", name.map(_.toUpperCase), cls := " text-blue-300")
      )
    }

  @main def main() = {
    routes(
      div(
        path("home") {
          HomePage.root
        },
        noneMatched {
          div("404")
        }
      )
    ).renderInto(
      dom.document
        .getElementById("app-container")
        .asInstanceOf[fs2.dom.Node[IO]]
    ).useForever
      .unsafeRunAndForget()
  }
}
