import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import cats.syntax.all.*
import fs2.dom.*
import scala.scalajs.js
import org.scalajs.dom.*
import org.scalajs.dom

import frontroute.*
import frontroute.given

import typings.animejs.mod.*
import typings.animejs.global.*
import typings.animejs.libAnimeMod
import org.scalajs.dom.HTMLElement
import components.BubbleKineticEffectComponent.animation
import configs.Theme
import pages.KnowledgePage
import pages.MessagePage

object HomePage {
  val tabs = List(
    "introduction" -> "Introduction",
    "knowledge" -> "Knowledge",
    "message" -> "Message"
  )

  val root: Resource[IO, HtmlElement[IO]] = div(
    cls := "bg-[#f5f5f5] dark:bg-[#303030] w-full h-full",
    div(
      cls := "w-full md:w-16 h-12 md:h-full flex justify-between md:justify-center items-center absolute md:fixed flex-row md:flex-col z-10",
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
                  "md:text-xl px-4 py-1 rounded bg-blue-200 text-slate-900 md:[writing-mode:vertical-rl] transform md:rotate-180 md:mb-4 flex-1 font-Roboto font-normal dark:text-slate-100"
                ),
                List(
                  "md:text-xl px-4 py-1 rounded text-slate-900 md:[writing-mode:vertical-rl] transform md:rotate-180 md:mb-4 flex-1 font-Roboto font-normal dark:text-slate-100"
                )
              )
            },
            tabLabel,
            onClick --> (_.foreach(_ => IO(println(dom.window.innerWidth)))),
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
      ),
      div(
        cls := "md:mt-8 mr-2 md:mr-8 md:fixed md:top-0 md:right-0 z-10 mt-2",
        div(
          (),
          onClick --> (_.foreach(_ => clickChangeThemeButton()))
        ).evalTap(
          _.innerHtml.set(
            if (Theme.isLight == true)
              """
                <svg id="themeSvg" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="stroke-2 w-4 h-4 md:w-6 md:h-6  dark:fill-neutral-100" viewBox="0 0 16 16">
                  <path id="themeSvgPath" d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
                </svg>
                """
            else
              """
                <svg id="themeSvg" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="stroke-2 w-4 h-4 md:w-6 md:h-6 dark:fill-neutral-100" viewBox="0 0 16 16">
                  <path id="themeSvgPath" d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"/>
                </svg>
                """
          )
        )
      )
    ),
    frontroute.path("introduction") {
      IntroductionPage.root
    },
    frontroute.path("knowledge") {
      KnowledgePage.root
    },
    frontroute.path("message") {
      MessagePage.root
    }
  )

  def clickChangeThemeButton(): IO[Unit] = {
    for {
      _ <- IO {
        if (Theme.isLight == true) {
          dom.document.documentElement.classList.remove("light")
          dom.document.documentElement.classList.add("dark")
          dom.window.localStorage.setItem("theme", "dark")
          libAnimeMod(
            AnimeParams()
              .setTargets(s"#themeSvg")
              .set(
                "opacity",
                "0"
              )
              .setDuration(700)
              .setEasing(EasingOptions.easeInOutSine)
          ).finished.`then`(x => {
            dom.document
              .getElementById("themeSvgPath")
              .asInstanceOf[SVGElement]
              .setAttribute(
                "d",
                "M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"
              )
            libAnimeMod(
              AnimeParams()
                .setTargets(s"#themeSvg")
                .set(
                  "opacity",
                  "1"
                )
                .setDuration(700)
                .setEasing(EasingOptions.easeInOutSine)
            )
          })
          Theme.isLight = false
        } else {
          dom.document.documentElement.classList.remove("dark")
          dom.document.documentElement.classList.add("light")
          dom.window.localStorage.setItem("theme", "light")
          libAnimeMod(
            AnimeParams()
              .setTargets(s"#themeSvg")
              .set(
                "opacity",
                "0"
              )
              .setDuration(700)
              .setEasing(EasingOptions.easeInOutSine)
          ).finished.`then`(x => {
            dom.document
              .getElementById("themeSvgPath")
              .asInstanceOf[SVGElement]
              .setAttribute(
                "d",
                "M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"
              )
            libAnimeMod(
              AnimeParams()
                .setTargets(s"#themeSvg")
                .set(
                  "opacity",
                  "1"
                )
                .setDuration(700)
                .setEasing(EasingOptions.easeInOutSine)
            )
          })
          Theme.isLight = true
        }
      }
    } yield ()
  }

}
