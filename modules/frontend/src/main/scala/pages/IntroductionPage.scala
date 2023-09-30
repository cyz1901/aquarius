import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
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

object IntroductionPage {
  val scene = IO.ref[Scene](null)
  val camera = IO.ref[PerspectiveCamera](null)
  val renderer = IO.ref[WebGLRenderer](null)

  val root: Resource[IO, HtmlElement[IO]] = div(
    "hello",
    renderWebGL()
  )

  def renderWebGL(): Resource[IO, HtmlElement[IO]] = {
    val webglCanvans: Resource[IO, HtmlCanvasElement[IO]] = Resource
      .eval(
        IO {
          val sw = window.screen.width
          val sh = window.screen.height

          val scene = Scene()
          val camera = PerspectiveCamera(75, sw / sh, 0.1, 1000)
          camera.position.z = 5

          // add object
          val geometry =
            BoxGeometry(
              1,
              1,
              1,
              js.undefined,
              js.undefined,
              js.undefined
            )
          val material =
            MeshBasicMaterial.apply(
              MeshBasicMaterialParameters.apply().setColor(0xff000)
            )
          val mesh = Mesh(geometry, material)
          val cube = mesh.asInstanceOf[Object3D[Object3DEventMap]]
          scene.add(cube)

          // add light
          val light = DirectionalLight(0xffffff, 0.5)
          light.position.set(0, 0, 5)
          scene.add(light)

          val renderer = new WebGLRenderer()
          renderer.setSize(sw, sh)
          renderer.render(scene, camera)
          val canvas = renderer.domElement
          canvas.asInstanceOf[HtmlCanvasElement[IO]]
        }
      )

    div(
      div(
        cls := " bg-slate-900 w-[100px] h-[100px] left-[50px] top-[50px] z-10 absolute"
      )
    )
  }

  def createWorld(): Unit = {
    IO {
      val _width = dom.window.innerWidth;
      val _height = dom.window.innerHeight;

      val scene = Scene();
      scene.background = srcMathColorMod.Color()

      val camera = PerspectiveCamera(35, _width / _height, 1, 1000);
      camera.position.set(0, 0, 16);

      val renderer = WebGLRenderer(
        WebGLRendererParameters().setAlpha(false).setAntialias(false)
      );
      renderer.setSize(_width, _height);
      renderer.shadowMap.enabled = true;

      dom.document.body.appendChild(renderer.domElement);

      dom.window.addEventListener("resize", onWindowResize, false);
    }
  }

  def onWindowResize() = {
     import scalajs.js.internal.UnitOps.unitOrOps
    for {
      width <- dom.window.innerWidth
      height <- dom.window.innerHeight;
      _ <- renderer.map(x => x.s(width, height));
    camera.aspect = _width / _height;
    camera.updateProjectionMatrix();
    }

  }
}
