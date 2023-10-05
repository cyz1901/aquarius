import { resolve } from "path";
import { defineConfig } from "vite";
import { createHtmlPlugin } from "vite-plugin-html";
import glsl from "vite-plugin-glsl";

import scalaVersion from "./scala-version";

export default ({ mode }) => {
  const mainJS = `modules/frontend/target/scala-${scalaVersion}/frontend-${
    mode === "production" ? "fastopt" : "fastopt"
  }/main.js`;
  const script = `<script type="module" src="/${mainJS}"></script>`;

  return defineConfig({
    resolve: {
      alias: {
        styles: resolve(__dirname, "./modules/frontend/styles"),
        svg: resolve(__dirname, "./modules/frontend/static/svg"),
        images: resolve(__dirname, "./modules/frontend/static/images"),
        glsl: resolve(__dirname, "./modules/frontend/glsl"),
      },
    },
    plugins: [
      glsl({ include: ["**/*.vert"] }),
      createHtmlPlugin({
        minify: mode === "production",
        inject: {
          data: {
            script,
          },
        },
      }),
    ],
  });
};
