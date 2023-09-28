import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {

  val calico: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.armanbilge" %%% "calico" % DependencyVersions.calico
    )
  }

  val scalaJsDom: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "org.scala-js" %%% "scalajs-dom" % DependencyVersions.scalaJsDom
    )
  }

  val frontrouteCalico: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "io.frontroute" %%% "frontroute-calico" % DependencyVersions.frontrouteCalico
    )
  }
}
