import sbt._

object Dependencies {

  val laminar: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.armanbilge" %%% "calico" % DependencyVersions.calico
    )
  }

}
