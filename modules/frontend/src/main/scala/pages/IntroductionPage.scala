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
import typings.three.srcMaterialsShaderMaterialMod.ShaderMaterialParameters
import typings.three.srcConstantsMod.Side
import org.scalablytyped.runtime.StringDictionary
import scala.scalajs.js.Date
import stores.IntroductionBackgroundOptions

object IntroductionPage {
  private val sceneRef: IO[Ref[IO, Option[Scene]]] =
    Ref.of[IO, Option[Scene]](None)
  private val cameraRef: IO[Ref[IO, Option[PerspectiveCamera]]] =
    Ref.of[IO, Option[PerspectiveCamera]](None)
  private val rendererRef: IO[Ref[IO, Option[WebGLRenderer]]] =
    Ref.of[IO, Option[WebGLRenderer]](None)

  private val shapeGroupRef: IO[Ref[IO, Option[Group[Any]]]] =
    Ref.of[IO, Option[Group[Any]]](Some(Group()))

  val root: Resource[IO, HtmlElement[IO]] = div(
    "hello",
    renderBackground()
    // renderWebGL()
    // renderWebGL1()
  )

  def renderBackground(): Resource[IO, HtmlElement[IO]] = {
    Resource.eval(
      for {
        elem <- createWorld()
        cameraRef <- cameraRef
        ca <- cameraRef.get
        // camera <- IO(ca.get)
        _ <- IO(dom.console.log(Thread.currentThread().getId()))
        // fRef <- primitiveElement.get
        // f <- fRef.get
        // _ <- f()
        // _ <- animation()
      } yield elem
    )
  }

  def createWorld(): IO[HtmlCanvasElement[IO]] = {
    val renderCanvans = for {
      width <- IO(dom.window.innerWidth)
      height <- IO(dom.window.innerHeight)

      sceneRef <- sceneRef
      _ <- sceneRef.update(_ => Some(Scene()))
      sc <- sceneRef.get
      _ <- IO(sc.foreach(_.background = srcMathColorMod.Color("#292733")))

      cameraRef <- cameraRef
      _ <- cameraRef.update(_ =>
        Some(PerspectiveCamera(35, width / height, 1, 1000))
      )
      ca <- cameraRef.get
      _ <- IO(ca.foreach(_.position.set(0, 0, 16)))

      _ <- IO(dom.console.log(ca.get))
      _ <- IO(dom.console.log(Thread.currentThread().getId()))

      rendererRef <- rendererRef
      _ <- rendererRef.modify(_ =>
        Some(
          WebGLRenderer(
            WebGLRendererParameters().setAlpha(false).setAntialias(false)
          )
        )
      )
      rd <- rendererRef.get
      _ <- IO(rd.foreach(rd => {
        rd.setSize(width, height)
        rd.shadowMap.enabled = true
      }))

      r <- rendererRef.get
      renderCanvans <- IO(
        r.get.domElement
      )

      _ <- IO(
        dom.window.addEventListener(
          "resize",
          onWindowResize,
          false
        )
      )
    } yield renderCanvans

    renderCanvans.map(x => x.asInstanceOf[HtmlCanvasElement[IO]])

  }

  def animation(): IO[Unit] = {
    val init = IO {
      shapeGroupRef.flatMap(x =>
        x.get.map(y => {
          val group = y.get
          val mat = group
            .getObjectByName("main")
            .getOrElse(Object3D())
            .asInstanceOf[ShaderMaterial]
          mat.uniforms.get("time").foreach(_.value = (0.4 / 1000) * 10)
          mat.uniforms.get("pointscale").foreach(_.value = 1.0)
          mat.uniforms.get("decay").foreach(_.value = 1.20)
          mat.uniforms.get("size").foreach(_.value = 0.7)
          mat.uniforms.get("displace").foreach(_.value = 1.0)
          mat.uniforms.get("complex").foreach(_.value = 0.5)
          mat.uniforms.get("waves").foreach(_.value = 3.7)
          mat.uniforms.get("fragment").foreach(_.value = true)
          mat.uniforms.get("redhell").foreach(_.value = true)
          mat.uniforms.get("eqcolor").foreach(_.value = 10.0)
          mat.uniforms.get("rcolor").foreach(_.value = 1.5)
          mat.uniforms.get("gcolor").foreach(_.value = 1.5)
          mat.uniforms.get("bcolor").foreach(_.value = 1.5)
        })
      )
      dom.window.requestAnimationFrame((_) => animation())
    }

    val g = for {
      // sceneRef <- sceneRef
      // // sc <- sceneRef.get
      // // scene <- IO(sc.get)
      // _ <- IO(dom.console.log(sceneRef.flatMap(_.get)))

      cameraRef <- cameraRef
      ca <- cameraRef.get
      // camera <- IO(ca.get)
      _ <- IO(dom.console.log(ca.get))
      // _ <- IO(dom.console.log(5))
      // rendererRef <- rendererRef
      // rd <- rendererRef.get
      // _ <- IO(rd.get.render(scene, camera))
    } yield ()

    init >> g
  }

  val primitiveElement = Ref.of[IO, () => IO[Unit]](() => {
    val step = for {
      _ <- IO(console.log("step"))
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
              dom.document.getElementById("vertexShader").textContent
            )
            .setFragmentShader(
              dom.document.getElementById("fragmentShader").textContent
            )
            .setName("main")
        )
      )
      wir_mat <- IO(
        MeshBasicMaterial(MeshBasicMaterialParameters().setColor("#000000"))
      )
      geo <- IO(IcosahedronGeometry(2, 6))
      wir <- IO(IcosahedronGeometry(2.3, 2))
      shape <- IO(Mesh(geo, mat))
      point <- IO(Points(wir, mat))

      shapeGroupRef <- shapeGroupRef
      sg <- shapeGroupRef.get
      _ <- IO(sg.foreach(s => {
        s.add(point)
        s.add(shape.asInstanceOf)
      }))

      sceneRef <- sceneRef
      _ <- sceneRef.update(_ => Some(Scene()))
      sc <- sceneRef.get
      _ <- IO(sc.foreach(s => {
        sg.foreach(g => s.add(g.asInstanceOf))
      }))
    } yield ()

    step
  })

  def onWindowResize(e: dom.UIEvent): Unit = {
    import scalajs.js.internal.UnitOps.unitOrOps
    for {
      width <- dom.window.innerWidth
      height <- dom.window.innerHeight
      renderer <- rendererRef
      rd <- renderer.get
      _ <- IO(rd.foreach(_.setSize(width, height)))
      camera <- cameraRef
      ca <- camera.get
      _ <- IO(ca.foreach(_.updateProjectionMatrix()))
    } yield ()
  }

  // def renderWebGL1(): Resource[IO, HtmlElement[IO]] = {
  //   val webglCanvans: Resource[IO, HtmlCanvasElement[IO]] = Resource
  //     .eval(
  //       for {
  //         sw <- IO(dom.window.screen.width)
  //         sh <- IO(dom.window.screen.height)

  //         sceneRef <- sceneRef
  //         _ <- sceneRef.update(_ => Some(Scene()))
  //         sc <- sceneRef.get
  //         _ <- IO(sc.foreach(_.background = srcMathColorMod.Color()))

  //         cameraRef <- cameraRef
  //         _ <- cameraRef.update(_ =>
  //           Some(PerspectiveCamera(75, sw / sh, 0.1, 1000))
  //         )
  //         ca <- cameraRef.get
  //         _ <- IO(ca.foreach(_.position.set(0, 0, 16)))
  //         // val camera = PerspectiveCamera(75, sw / sh, 0.1, 1000)
  //         // camera.position.z = 5

  //         // // add object
  //         // val geometry =
  //         //   BoxGeometry(
  //         //     1,
  //         //     1,
  //         //     1,
  //         //     js.undefined,
  //         //     js.undefined,
  //         //     js.undefined
  //         //   )
  //         // val material =
  //         //   MeshBasicMaterial.apply(
  //         //     MeshBasicMaterialParameters.apply().setColor(0xff000)
  //         //   )
  //         // val mesh = Mesh(geometry, material)
  //         // val cube = mesh.asInstanceOf[Object3D[Object3DEventMap]]
  //         // scene.add(cube)

  //         // // add light
  //         // // val light = DirectionalLight(0xffffff, 0.5)
  //         // // light.position.set(0, 0, 5)
  //         // // scene.add(light)

  //         // val renderer = new WebGLRenderer()
  //         // renderer.setSize(sw, sh)
  //         // renderer.render(scene, camera)
  //         // val canvas = renderer.domElement
  //         // canvas.asInstanceOf[HtmlCanvasElement[IO]]
  //       }
  //     )

  //   // div(
  //   //   div(
  //   //     cls := " bg-slate-900 w-[100px] h-[100px] left-[50px] top-[50px] z-10 absolute"
  //   //   )
  //   // )
  //   webglCanvans
  // }

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
          // val light = DirectionalLight(0xffffff, 0.5)
          // light.position.set(0, 0, 5)
          // scene.add(light)

          val renderer = new WebGLRenderer()
          renderer.setSize(sw, sh)
          renderer.render(scene, camera)
          val canvas = renderer.domElement
          canvas.asInstanceOf[HtmlCanvasElement[IO]]
        }
      )

    // div(
    //   div(
    //     cls := " bg-slate-900 w-[100px] h-[100px] left-[50px] top-[50px] z-10 absolute"
    //   )
    // )
    webglCanvans
  }
}
