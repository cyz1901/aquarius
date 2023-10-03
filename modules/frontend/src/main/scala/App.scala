import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import cats.effect.syntax.all.*
import cats.syntax.all.*
import fs2.*
import fs2.concurrent.*
import fs2.dom.*

import frontroute.*
import frontroute.given

import org.scalajs.dom.*
import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
@js.native
@JSImport("styles/index.css", JSImport.Default)
object IndexCss extends js.Object

object App {
  val indexCss: IndexCss.type = IndexCss

  @main def main() = {
    routes(
      pathEnd {
        runEffect {
          IO {
            window.location.href = window.location.href + "home/introduction";
          }
        }
      },
      pathPrefix("home") {
        HomePage.root
      },
      noneMatched {
        div("404")
      }
    ).renderInto(
      dom.document
        .getElementById("app-container")
        .asInstanceOf[fs2.dom.Node[IO]]
    ).useForever
      .unsafeRunAndForget()
  }
}
