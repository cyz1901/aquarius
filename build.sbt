import org.scalajs.linker.interface.ESVersion
import sbtcrossproject.CrossPlugin.autoImport.crossProject
import sbtcrossproject.CrossPlugin.autoImport.CrossType

name := "aquarius"

inThisBuild(
  List(
    organization := "cyz",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "3.3.1",
    scalafmtOnCompile := true
  )
)

lazy val frontend =
  project
    .in(
      file("modules/frontend")
    )
    .enablePlugins(ScalaJSPlugin)
    .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
    .disablePlugins(RevolverPlugin)
    .settings(
      Compile / fastLinkJS / scalaJSLinkerConfig ~= {
        _.withModuleKind(ModuleKind.ESModule)
      },
      Compile / fullLinkJS / scalaJSLinkerConfig ~= {
        _.withModuleKind(ModuleKind.CommonJSModule)
      },
      scalaJSLinkerConfig ~= {
        _.withESFeatures(_.withESVersion(ESVersion.ES5_1))
      },
      Compile / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
      scalaJSUseMainModuleInitializer := true,
      externalNpm := {
        baseDirectory.value.getParentFile.getParentFile
      },
      libraryDependencies ++= Seq.concat(
        Dependencies.calico.value,
        Dependencies.scalaJsDom.value,
        Dependencies.frontrouteCalico.value
      )
    )
