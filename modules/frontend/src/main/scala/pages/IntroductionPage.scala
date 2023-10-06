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
import typings.std.stdStrings.descriptions
import typings.animejs.mod.AnimeInstance

@js.native
@JSImport("images/beautiful-view.jpg", JSImport.Default)
object AboutMeImage extends js.Object

object IntroductionPage {
  var imgSize: Tuple2[Int, Int] = (0, 0)
  var isImgAnime: Boolean = true

  val root: Resource[IO, HtmlElement[IO]] = {
    div(
      cls := "absolute z-0 dark:bg-slate-600",
      div(
        idAttr := "k",
        cls := "absolute w-full md:w-[calc(100%_-_64px)] h-screen right-0 flex flex-row justify-center",
        div(
          cls := "w-[50%] h-[50vh]  mt-[40vh] md:mt-[25vh] flex flex-col justify-start mp-[10vh]",
          p("cyz1901", cls := "md:text-2xl text-4xl font-Roboto dark:text-slate-200"),
          div(
            cls := "mt-3 md:mt-12 flex flex-row items-center",
            p("Artists", cls := "md:text-6xl lg:text-8xl text-4xl font-Roboto dark:text-slate-200"),
            div(cls := " bg-slate-800 h-[1px] w-[30vh] ml-3")
          ),
          div(
            cls := "mt-3 flex flex-row items-baseline",
            p("+", cls := "md:text-6xl lg:text-8xl text-4xl font-Roboto text-slate-600 dark:text-neutral-950"),
            p(
              "programmers",
              cls := "md:text-6xl lg:text-8xl text-4xl font-Roboto dark:text-slate-200",
              onLoad --> (_.foreach(_ => IO(println(7777))))
            )
          )
        ),
        div(cls := "absolute bottom-3 md:left-[calc(50%_-_44px)] left-[calc(50%_-_12px)]", renderScrollingTipIcon())
      ),
      BubbleKineticEffectComponent.root,
      renderAboutMe()
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
            svg.setAttribute("class", "bi bi-brightness-high stroke-2 dark:fill-neutral-100")
            svg.setAttribute("viewBox", "0 0 16 16")
            svg.setAttribute("id", "scrollingTip")
            d.appendChild(svg)
          }
          _ <- IO(
            IO {

              var anime = tipAnimetion(d.firstChild.asInstanceOf[SVGElement], 1)

              // scroll
              dom.window.addEventListener(
                "scroll",
                _ => {
                  val nowOpacity =
                    dom.window
                      .getComputedStyle(d.firstChild.asInstanceOf[SVGElement])
                      .getPropertyValue("opacity")
                      .toDouble

                  val yAm = libAnimeMod(
                    AnimeParams()
                      .setTargets(s"#${d.firstChild.asInstanceOf[SVGElement].id}")
                      .set(
                        "opacity",
                        js.Array(nowOpacity, 0)
                      )
                      .setDuration(750)
                      .setEasing(EasingOptions.easeInOutSine)
                  )
                  if (dom.window.scrollY > 0) {

                    anime.pause()
                    yAm.play()
                  } else {
                    yAm.pause()
                    anime = tipAnimetion(d.firstChild.asInstanceOf[SVGElement], nowOpacity)
                    anime.restart()
                  }
                }
              )
            }.unsafeRunAsync(_ => {})
          )
        } yield (d.asInstanceOf[HtmlElement[IO]])
      )

  }

  def renderAboutMe(): Resource[IO, HtmlElement[IO]] = {
    val description =
      "Hello, I'm Chen YiZhou, and I'm excited to introduce myself.I've had the opportunity to work in both Malaysia and China during my career, and I've also been part of some major multinational companies.\nI'm a versatile programmer, capable of handling various aspects of software projects.\nMy tech skills encompass languages like Scala, Flutter, and TypeScript.But what truly ignites my passion are certain interests: I'm a staunch advocate of functional programming, emphasizing efficient and clean code practices. \nAdditionally, I have a keen interest in game development, quantitative trading, and delving deep into the realm of artificial intelligence.Besides that, I also have a strong interest in music and photography."

    div(
      cls := "w-full md:pt-64 pt-32  flex flex-col items-start justify-center dark:bg-[#303030]",
      div(
        cls := "ml-[10vw] md:ml-[20vw] flex flex-col items-start justify-start",
        p("About me", idAttr := "aboutMeTitleId", cls := "md:text-6xl text-3xl font-Roboto mb-3 dark:text-slate-100"),
        div(
          cls := "w-[80vw] md:w-[60vw] flex md:flex-row flex-col",
          p(
            idAttr := "aboutMeDescribleId",
            cls := "basis-1/2 font-Roboto md:mr-8 whitespace-pre-line opacity-0 dark:text-slate-100",
            description
          ),
          div(
            cls := "relative basis-1/2 h-auto bg-[#f5f5f5] dark:bg-[#303030]",
            img(
              idAttr := "about-me-image",
              cls := " absolute w-auto h-auto mt-4 md:mt-0 z-0 ",
              src := AboutMeImage.toString()
            ),
            div(
              idAttr := "about-me-animation",
              cls := "absolute bg-purple-100 left-0 top-0 z-10 mt-4 md:mt-0"
            )
          )
            .evalTap(x =>
              IO {
                dom.window.addEventListener(
                  "scroll",
                  _ => {
                    if (dom.window.scrollY > 0) {
                      val img = dom.document.getElementById("about-me-image")
                      imgSize = (img.clientWidth, img.clientHeight)

                      val div = dom.document.getElementById("about-me-animation")
                      div.setAttribute("style", s"height: ${imgSize._2}px")

                      if (isImgAnime) {
                        fontRevealAnimation("aboutMeTitleId").finished.`then`(x =>
                          fontRevealAnimation("aboutMeDescribleId")
                        )

                        libAnimeMod(
                          AnimeParams()
                            .setTargets(s"#about-me-animation")
                            .set(
                              "width",
                              js.Array(0, imgSize._1)
                            )
                            .setDuration(1000)
                            .setEasing(EasingOptions.easeInOutSine)
                        ).finished.`then`(x => {
                          div.setAttribute(
                            "class",
                            s"absolute bg-purple-100 right-0 top-0 z-10 mt-4 md:mt-0"
                          )
                          libAnimeMod(
                            AnimeParams()
                              .setTargets(s"#about-me-animation")
                              .set(
                                "width",
                                js.Array(imgSize._1, 0)
                              )
                              .setDuration(1000)
                              .setEasing(EasingOptions.easeInOutSine)
                          )
                        })
                        isImgAnime = false
                      }

                    }
                  }
                )
              }
            )
        )
      )
    )

  }

  def tipAnimetion(e: Element, nowOpacity: Double): AnimeInstance = {

    return libAnimeMod(
      AnimeParams()
        .setTargets(s"#${e.id}")
        .set(
          "opacity",
          js.Array(nowOpacity, if (nowOpacity == 1) 0 else 1)
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
  }

  def fontRevealAnimation(id: String): AnimeInstance = {

    return libAnimeMod(
      AnimeParams()
        .setTargets(s"#${id}")
        .set(
          "opacity",
          js.Array(0, 1)
        )
        .setDuration(700)
        .setEasing(EasingOptions.easeInOutSine)
    )
  }
}
