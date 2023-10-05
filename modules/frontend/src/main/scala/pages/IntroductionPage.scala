import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import cats.syntax.either._
import fs2.dom.*
import scala.scalajs.js
import org.scalajs.dom.*
import org.scalajs.dom

import typings.three.mod.*
import typings.three.mod.BoxGeometry
import typings.three.srcMaterialsMeshBasicMaterialMod.MeshBasicMaterialParameters
import typings.three.srcCoreObject3DMod.Object3DEventMap
import typings.std.stdStrings.canvas
import typings.three.srcMathColorMod
import typings.three.srcRenderersWebGLRendererMod.WebGLRendererParameters
import typings.three.srcMaterialsShaderMaterialMod.ShaderMaterialParameters
import typings.three.srcConstantsMod.Side
import org.scalablytyped.runtime.StringDictionary
import scala.scalajs.js.Date
import scala.scalajs.js.annotation.JSImport
import components.BubbleKineticEffectComponent
import typings.std.stdStrings.path
import org.scalajs.dom.SVGElement
import configs.Theme
import typings.animejs.libAnimeMod
import typings.animejs.mod.AnimeParams
import typings.animejs.mod.EasingOptions
import calico.html.EventProp.PipeModifier
import org.scalajs.dom.Element

object IntroductionPage {
  val root: Resource[IO, HtmlElement[IO]] = {
    div(
      div(
        idAttr := "k",
        cls := "absolute w-full md:w-[calc(100%_-_64px)] h-screen right-0 flex flex-row justify-center",
        div(
          cls := "mt-3 md:mt-8 mr-2 md:mr-8 fixed top-0 right-0",
          div(
            (),
            onClick --> (_.foreach(_ => clickChangeThemeButton()))
          ).evalTap(
            _.innerHtml.set(
              if (Theme.isLight == true)
                """
                <svg id="themeSvg" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="stroke-2 w-4 h-4 md:w-6 md:h-6" viewBox="0 0 16 16">
                  <path id="themeSvgPath" d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
                </svg>
                """
              else
                """
                <svg id="themeSvg" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="stroke-2 w-4 h-4 md:w-6 md:h-6" viewBox="0 0 16 16">
                  <path id="themeSvgPath" d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"/>
                </svg>
                """
            )
          )
        ),
        div(
          cls := "w-[50%] h-[50vh]  mt-[40vh] md:mt-[25vh] flex flex-col justify-start mp-[10vh]",
          p("cyz1901", cls := "md:text-2xl text-4xl font-Roboto"),
          div(
            cls := "mt-3 md:mt-12 flex flex-row items-center",
            p("Artists", cls := "md:text-8xl text-4xl font-Roboto"),
            div(cls := " bg-slate-800 h-[1px] w-[30vh] ml-3")
          ),
          div(
            cls := "mt-3 flex flex-row items-baseline",
            p("+", cls := "md:text-8xl text-4xl font-Roboto text-slate-400"),
            p("programmers", cls := "md:text-8xl text-4xl font-Roboto", onLoad --> (_.foreach(_ => IO(println(7777)))))
          )
        ),
        div(cls := "absolute bottom-3 left-[calc(50%_-_12px]", renderScrollingTipIcon())
      ),
      BubbleKineticEffectComponent.root
      // renderAboutMe()
    )
  }

  def renderScrollingTipIcon(): Resource[IO, HtmlElement[IO]] = {
    Resource
      .eval(
        for {
          d <- IO(dom.document.createElement("div"))
          _ <- IO {
            val path = document.createElementNS("http://www.w3.org/2000/svg", "path")
            path.setAttribute(
              "d",
              "M8 3a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 3zm4 8a4 4 0 0 1-8 0V5a4 4 0 1 1 8 0v6zM8 0a5 5 0 0 0-5 5v6a5 5 0 0 0 10 0V5a5 5 0 0 0-5-5z"
            )
            val svg = dom.document.createElementNS("http://www.w3.org/2000/svg", "svg")
            svg.appendChild(path)
            svg.setAttribute("width", "24")
            svg.setAttribute("height", "24")
            svg.setAttribute("fill", "currentColor")
            svg.setAttribute("class", "bi bi-brightness-high stroke-2")
            svg.setAttribute("viewBox", "0 0 16 16")
            svg.setAttribute("id", "scrollingTip")
            d.appendChild(svg)
          }
          _ <- IO(
            IO(
              libAnimeMod(
                AnimeParams()
                  .setTargets(s"#${d.firstChild.asInstanceOf[SVGElement].id}")
                  .set(
                    "opacity",
                    js.Array(1, 0)
                  )
                  .set(
                    "translateY",
                    js.Array(0, -10)
                  )
                  .setAutoplay(true)
                  .setLoop(true)
                  .setDuration(1500)
                  .setEasing(EasingOptions.easeInOutSine)
                  .setDirection("alternate")
              )
            ).unsafeRunAsync(_ => {})
          )

        } yield (d.asInstanceOf[HtmlElement[IO]])
      )

  }

  def renderAboutMe(): Resource[IO, HtmlElement[IO]] = {
    div(
      cls := "w-full pt-24 bg-[#FFFAF0]",
      div(
        cls := "w-[75vh] flex flex-col items-start justify-center",
        p("About me")
      )
    )

  }

  def clickChangeThemeButton(): IO[Unit] = {
    for {
      _ <- IO {
        if (Theme.isLight == true) {
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
