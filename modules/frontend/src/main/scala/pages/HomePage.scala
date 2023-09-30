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
import typings.animejs.global.*
import typings.animejs.libAnimeMod
import org.scalajs.dom.HTMLElement

object HomePage {
  val tabs = List(
    "introduction" -> "Introduction",
    "knowledge" -> "Knowledge",
    "message" -> "Message"
  )

  val root: Resource[IO, HtmlElement[IO]] = div(
    div(
      cls := "flex flex-wrap justify-center items-center w-16",
      tabs.map { case (path, tabLabel) =>
        import org.scalajs.dom
        a(
          idAttr := s"$path-a",
          relativeHref(path),
          navMod { active =>
            cls <-- active.ifF(
              List(
                "text-xl px-4 py-1 rounded bg-blue-200 text-slate-900 [writing-mode:vertical-rl] transform rotate-180 mb-4 flex-1"
              ),
              List(
                "text-xl px-4 py-1 rounded text-slate-900 [writing-mode:vertical-rl] transform rotate-180 mb-4 flex-1"
              )
            )
          },
          tabLabel,
          onMouseEnter --> (_.foreach(_ =>
            IO {
              val height = Math
                .floor(
                  dom.window
                    .getComputedStyle(dom.document.getElementById(s"$path-a"))
                    .height
                    .replace("px", "")
                    .toDouble - dom.window
                    .getComputedStyle(dom.document.getElementById(s"$path-a"))
                    .paddingTop
                    .replace("px", "")
                    .toDouble - dom.window
                    .getComputedStyle(dom.document.getElementById(s"$path-a"))
                    .paddingBottom
                    .replace("px", "")
                    .toDouble
                )

              libAnimeMod(
                AnimeParams()
                  .setTargets(s"#${path}")
                  .set(
                    "height",
                    s"${height}px"
                  )
                  .setDuration(1000)
                  .setEasing(EasingOptions.easeInOutExpo)
              )
            }
          )),
          onMouseLeave --> (_.foreach(_ =>
            IO {
              libAnimeMod(
                AnimeParams()
                  .setTargets(s"#${path}")
                  .set(
                    "height",
                    s"0px"
                  )
                  .setDuration(1000)
                  .setEasing(EasingOptions.easeInOutExpo)
              )
            }
          )),
          div(
            idAttr := path,
            cls := "w-2 h-[0px] bg-purple-800"
          )
        )
      }
    ),
    frontroute.path("introduction") {
      IntroductionPage.root
    }
  )
}
