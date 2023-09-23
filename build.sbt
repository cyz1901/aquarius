val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "aquarius",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "com.armanbilge" %%% "calico" % "0.2.1"
  )

// lazy val frontend =
//   project
//     .in(
//       file("modules/frontend")
//     )
//     .enablePlugins(ScalaJSPlugin)
//     .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
//     .disablePlugins(RevolverPlugin)
//     .settings(
//       Compile / fastLinkJS / scalaJSLinkerConfig ~= {
//         _.withModuleKind(ModuleKind.ESModule)
//       },
//       Compile / fullLinkJS / scalaJSLinkerConfig ~= {
//         _.withModuleKind(ModuleKind.CommonJSModule)
//       },
//       scalaJSLinkerConfig ~= {
//         _.withESFeatures(_.withESVersion(ESVersion.ES5_1))
//       },
//       Compile / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
//       scalaJSUseMainModuleInitializer := true,
//       externalNpm := {
//         baseDirectory.value.getParentFile.getParentFile
//       },
//       libraryDependencies ++= Seq.concat(
//         Dependencies.laminar.value
//       )
//     )
//     .settings(sharedSettings)
//     .dependsOn(shared.js)
