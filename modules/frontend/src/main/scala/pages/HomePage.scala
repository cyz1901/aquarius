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
import components.BubbleKineticEffectComponent.animation

object HomePage {
  val tabs = List(
    "introduction" -> "Introduction",
    "knowledge" -> "Knowledge",
    "message" -> "Message"
  )

  val root: Resource[IO, HtmlElement[IO]] = div(
    cls := "bg-[#FFFAF0] w-full h-full",
    div(
      cls := "w-full md:w-16 h-12 md:h-full flex md:justify-center items-center fixed md:flex-row",
      div(
        cls := "flex md:flex-wrap  justify-start md:justify-center items-center w-16 md:fixed md:top-0 md:left-0 pt-2 md:pt-8",
        tabs.map { case (path, tabLabel) =>
          import org.scalajs.dom
          a(
            idAttr := s"$path-a",
            relativeHref(path),
            navMod { active =>
              cls <-- active.ifF(
                List(
                  "md:text-xl px-4 py-1 rounded bg-blue-200 text-slate-900 md:[writing-mode:vertical-rl] transform md:rotate-180 mb-4 flex-1 font-Roboto font-normal"
                ),
                List(
                  "md:text-xl px-4 py-1 rounded text-slate-900 md:[writing-mode:vertical-rl] transform md:rotate-180 mb-4 flex-1 font-Roboto font-normal"
                )
              )
            },
            tabLabel,
            onMouseEnter --> (_.foreach(_ =>
              for {
                _ <- IO {
                  if (dom.window.innerWidth > 760) {
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
                }
              } yield ()
            )),
            onMouseLeave --> (_.foreach(_ =>
              IO {
                if (dom.window.innerWidth > 760) {
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
              }
            )),
            div(
              idAttr := path,
              cls := "w-[6px] h-[0px] bg-purple-300 absolute right-[28px] top-0 rounded"
            )
          )
        }
      )
    ),
    frontroute.path("introduction") {
      IntroductionPage.root
    }
  )
}
