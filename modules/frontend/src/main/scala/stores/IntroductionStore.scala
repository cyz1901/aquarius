package stores

import scala.scalajs.js
import typings.three.mod.*

object IntroductionStore {}

object IntroductionBackgroundOptions {
  val perlin = js.Dictionary(
    "speed" -> 0.4,
    "size" -> 0.7,
    "perlins" -> 1.0,
    "decay" -> 1.20,
    "displace" -> 1.00,
    "complex" -> 0.50,
    "waves" -> 3.7,
    "eqcolor" -> 10.0,
    "rcolor" -> 1.5,
    "gcolor" -> 1.5,
    "bcolor" -> 1.5,
    "fragment" -> true,
    "points" -> false,
    "redhell" -> true
  )

}
