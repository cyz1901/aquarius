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

object IntroductionPage {
  val root: Resource[IO, HtmlElement[IO]] = {
    div(
      div(
        cls := "absolute w-[calc(100%_-_64px)] h-screen right-0 flex flex-row justify-center",
        div(
          cls := "mt-8 fixed top-0 right-0"
          // todo add svg
        ),
        div(
          cls := "w-[50%] h-[50vh] mt-[25vh] flex flex-col justify-start mp-[10vh]",
          p("cyz1901", cls := "text-2xl font-Roboto"),
          div(
            cls := " mt-12 flex flex-row items-center",
            p("Artists", cls := "text-8xl font-Roboto"),
            div(cls := " bg-slate-800 h-[1px] w-[30vh] ml-3")
          ),
          div(
            cls := "mt-3 flex flex-row items-baseline",
            p("+", cls := "text-8xl font-Roboto text-slate-400"),
            p("programmers", cls := "text-8xl font-Roboto")
          )
        )
      ),
      BubbleKineticEffectComponent.root
    )
  }
}
