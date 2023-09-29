import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import cats.syntax.all.*
import fs2.dom.*
import scala.scalajs.js
import org.scalajs.dom.*

import frontroute.*
import frontroute.given

import typings.animejs.mod.*
import typings.animejs.global.anime

object HomePage {
  val tabs = List(
    "introduction" -> "Introduction",
    "knowledge" -> "Knowledge",
    "message" -> "Message"
  )

  val root: Resource[IO, HtmlElement[IO]] = div(
    div(
      cls := "grid grid-cols-1",
      tabs.map { case (path, tabLabel) =>
        a(
          idAttr := path,
          relativeHref(path),
          navMod { active =>
            cls <-- active.ifF(
              List(
                "text-xl px-4 py-1 rounded border-b-2 border-blue-800 bg-blue-200 text-slate-900 [writing-mode:vertical-rl] transform rotate-180"
              ),
              List(
                "text-xl px-4 py-1 rounded border-b-2 border-transparent text-slate-900 [writing-mode:vertical-rl] transform rotate-180"
              )
            )
          },
          tabLabel,
          onMouseEnter --> (_.foreach(_ =>
            IO {
              import org.scalajs.dom
              org.scalajs.dom.console.log("aaa")
              // anime()
            }
          )),
          div(
            cls := "w-2 h-2 bg-red-500"
          )
        )
      }
    )
  )
}
