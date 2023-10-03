package components

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

@js.native
@JSImport("glsl/bgVertexShader.vert", JSImport.Default)
object VertexShader extends js.Object

@js.native
@JSImport("glsl/bgFragmentShader.vert", JSImport.Default)
object FragmentShader extends js.Object

trait IntroductionBackgroundDependency {
  val scene: Option[Scene]
  val camera: Option[PerspectiveCamera]
  val renderer: Option[WebGLRenderer]
  val shapeGroup: Option[Group[Any]]
}

case class IntroductionBackgroundData(
    val scene: Option[Scene] = None,
    val camera: Option[PerspectiveCamera] = None,
    val renderer: Option[WebGLRenderer] = None,
    val shapeGroup: Option[Group[Any]] = Some(Group())
) extends IntroductionBackgroundDependency {}

object BubbleKineticEffectComponent {
  val startTimeStamp = Date.now()

  val root: Resource[IO, HtmlElement[IO]] = renderBackground()

  val primitiveElement = Ref.of[IO, (IntroductionBackgroundData) => IO[Unit]]((data) => {
    val step =
      for {
        mesh <- IO(Object3D())
        mat <- IO(
          ShaderMaterial(
            ShaderMaterialParameters()
              .setSide(Side.`2`)
              .setUniforms(
                StringDictionary()
                  .set("time", ("type" -> "f", "value" -> 0.1))
                  .set("pointscale", ("type" -> "f", "value" -> 0.2))
                  .set("decay", ("type" -> "f", "value" -> 0.3))
                  .set("size", ("type" -> "f", "value" -> 0.3))
                  .set("displace", ("type" -> "f", "value" -> 0.3))
                  .set("complex", ("type" -> "f", "value" -> 0.0))
                  .set("waves", ("type" -> "f", "value" -> 0.10))
                  .set("eqcolor", ("type" -> "f", "value" -> 0.0))
                  .set("rcolor", ("type" -> "f", "value" -> 0.0))
                  .set("gcolor", ("type" -> "f", "value" -> 0.0))
                  .set("bcolor", ("type" -> "f", "value" -> 0.0))
                  .set("fragment", ("type" -> "i", "value" -> true))
                  .set("redhell", ("type" -> "i", "value" -> true))
              )
              .setVertexShader(
                VertexShader.toString()
              )
              .setFragmentShader(
                FragmentShader.toString()
              )
          )
        )
        geo <- IO(IcosahedronGeometry(2, 15))
        wir <- IO(IcosahedronGeometry(2.3, 5))
        shape <- IO(Mesh(geo, mat))
        point <- IO(Points(wir, mat))

        g <- IO(
          data.shapeGroup
            .map(s => {
              s.add(point)
              s.add(shape.asInstanceOf)
            })
            .get
        )

        _ <- IO(data.scene.foreach(s => {
          s.add(g.asInstanceOf)
        }))
      } yield ()

    step
  })

  def renderBackground(): Resource[IO, HtmlCanvasElement[IO]] = {
    Resource.eval(
      for {
        e <- createWorld()
        fRef <- primitiveElement.get
        f <- fRef.get
        _ <- f(e._2)
        _ <- animation()(using e._2)
        _ <- IO(dom.window.requestAnimationFrame((_) => animation()(using e._2).unsafeRunAsync(_ => {})))
      } yield e._1
    )
  }

  def createWorld(): IO[(HtmlCanvasElement[IO], IntroductionBackgroundData)] = {
    val t = for {
      width <- IO(dom.window.innerWidth)
      height <- IO(dom.window.innerHeight)

      data <- IO(
        IntroductionBackgroundData(
          scene = Some(Scene()),
          camera = Some(PerspectiveCamera(35, width / height, 1, 1000)),
          renderer = Some(
            WebGLRenderer(
              WebGLRendererParameters().setAlpha(false).setAntialias(false)
            )
          )
        )
      )

      _ <- IO(data.scene.foreach(_.background = srcMathColorMod.Color("#FFFAF0")))
      _ <- IO(data.camera.foreach(_.position.set(0, 0, 16)))
      _ <- IO(data.renderer.foreach(r => {
        r.setSize(width, height)
        r.shadowMap.enabled = true
      }))

      renderCanvans <- IO(
        data.renderer.get.domElement
      )
      _ <- IO(renderCanvans.style.zIndex = "20")
      _ <- IO(
        dom.window.addEventListener(
          "resize",
          (e: dom.UIEvent) => onWindowResize()(using data).unsafeRunAsync(_ => {}),
          false
        )
      )
    } yield (renderCanvans, data)

    t.map { case (x: HTMLCanvasElement, y: IntroductionBackgroundData) =>
      (x.asInstanceOf[HtmlCanvasElement[IO]], y)
    }
  }

  def animation()(using data: IntroductionBackgroundData): IO[Unit] = {
    val init = IO {
      data.shapeGroup.get.children.foreach(x =>
        x match
          case a: Points[?, ?] => {
            val mat = a.material.asInstanceOf[ShaderMaterial]
            mat.uniforms.get("time").foreach(_.value = (0.12 / 1000) * (Date.now() - startTimeStamp))
            mat.uniforms.get("pointscale").foreach(_.value = 1.0)
            mat.uniforms.get("decay").foreach(_.value = 1.80)
            mat.uniforms.get("size").foreach(_.value = 1)
            mat.uniforms.get("displace").foreach(_.value = 1.8)
            mat.uniforms.get("complex").foreach(_.value = 0.69)
            mat.uniforms.get("waves").foreach(_.value = 20)
            mat.uniforms.get("fragment").foreach(_.value = true)
            mat.uniforms.get("redhell").foreach(_.value = true)
            mat.uniforms.get("eqcolor").foreach(_.value = 10.0)
            mat.uniforms.get("rcolor").foreach(_.value = 1.5)
            mat.uniforms.get("gcolor").foreach(_.value = 1.5)
            mat.uniforms.get("bcolor").foreach(_.value = 1.5)
          }
          case b: Mesh[?, ?, ?] => {
            val mat = b.material.asInstanceOf[ShaderMaterial]
            mat.uniforms.get("time").foreach(_.value = (0.12 / 1000) * (Date.now() - startTimeStamp))
            mat.uniforms.get("pointscale").foreach(_.value = 1.0)
            mat.uniforms.get("decay").foreach(_.value = 1.80)
            mat.uniforms.get("size").foreach(_.value = 1)
            mat.uniforms.get("displace").foreach(_.value = 1.8)
            mat.uniforms.get("complex").foreach(_.value = 0.69)
            mat.uniforms.get("waves").foreach(_.value = 20)
            mat.uniforms.get("fragment").foreach(_.value = true)
            mat.uniforms.get("redhell").foreach(_.value = true)
            mat.uniforms.get("eqcolor").foreach(_.value = 10.0)
            mat.uniforms.get("rcolor").foreach(_.value = 1.5)
            mat.uniforms.get("gcolor").foreach(_.value = 1.5)
            mat.uniforms.get("bcolor").foreach(_.value = 1.5)
          }
      )

      dom.window.requestAnimationFrame((_) => animation()(using data).unsafeRunAsync(_ => {}))

      data.renderer.get.render(data.scene.get, data.camera.get)
    }
    init
  }

  def onWindowResize()(using data: IntroductionBackgroundData): IO[Unit] = {
    for {
      width <- IO(dom.window.innerWidth)
      height <- IO(dom.window.innerHeight)
      _ <- IO(data.renderer.get.setSize(width, height))
      _ <- IO(data.camera.get.updateProjectionMatrix())
    } yield ()
  }
}
