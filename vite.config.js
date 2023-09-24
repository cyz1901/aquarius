import { resolve } from "path";
import { defineConfig } from "vite";
import { createHtmlPlugin } from "vite-plugin-html";

import scalaVersion from "./scala-version";

export default ({ mode }) => {
  const mainJS = `modules/frontend/target/scala-${scalaVersion}/frontend-${
    mode === "production" ? "opt" : "fastopt"
  }/main.js`;
  const script = `<script type="module" src="/${mainJS}"></script>`;

  return defineConfig({
    resolve: {
      alias: {
        styles: resolve(__dirname, "./modules/frontend/styles"),
        svg: resolve(__dirname, "./modules/frontend/static/svg"),
      },
    },
    plugins: [
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
