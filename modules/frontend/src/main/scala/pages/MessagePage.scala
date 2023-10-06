package pages

import calico.*
import calico.html.io.{*, given}
import calico.unsafe.given
import calico.syntax.*
import cats.effect.*
import fs2.dom.*

object MessagePage {
  val root: Resource[IO, HtmlElement[IO]] = div(
    cls := "w-[100vw] h-[100vh] flex flex-col items-center justify-center",
    p(
      cls := "text-4xl md:text-8xl font-Roboto dark:text-slate-200",
      "404"
    ),
    p(
      cls := "text-2xl md:text-4xl font-Roboto dark:text-slate-200",
      "Message Page not ready yet :("
    )
  )
}
