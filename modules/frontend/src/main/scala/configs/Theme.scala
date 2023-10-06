package configs
import org.scalajs.dom
//todo add localStorage or sessionStorage to store theme
object Theme {
  var isLight = initTheme()

  def initTheme(): Boolean = {
    if (
      dom.window.localStorage
        .getItem("theme") == "dark" || (!(dom.window.localStorage
        .getItem("theme") != null)) && dom.window.matchMedia("(prefers-color-scheme: dark)").matches
    ) {

      dom.document.documentElement.classList.add("dark")
      return false
    } else {

      dom.document.documentElement.classList.remove("dark")
      return true
    }
  }
}
