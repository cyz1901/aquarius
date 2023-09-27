import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import fs2.dom.*
import scala.scalajs.js

import typings.three.mod.*
import typings.three.mod.BoxGeometry
import typings.three.srcMaterialsMeshBasicMaterialMod.MeshBasicMaterialParameters
import typings.three.srcCoreObject3DMod.Object3DEventMap
object HomePage {
  val root: Resource[IO, HtmlElement[IO]] = div(
    "hello"
  )

  def renderWebGL(): Resource[IO, HtmlCanvasElement[IO]] = {
    val scene = Scene()
    val geometry =
      BoxGeometry(100.0, 100.0, 100.0, js.undefined, js.undefined, js.undefined)
    val material =
      MeshBasicMaterial.apply(
        MeshBasicMaterialParameters.apply().setColor(0xff0000)
      )
    val mesh = Mesh(geometry, material)
    val object3d = mesh.asInstanceOf[Object3D[Object3DEventMap]]
    scene.add(object3d)
    val renderer = new WebGLRenderer()

  }
}
