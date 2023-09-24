const scalaVersion = require("./scala-version");

module.exports = (api) => {
  const scalajsMode = api.env === "production" ? "opt" : "fastopt";
  return {
    content: [
      `./modules/frontend/target/scala-${scalaVersion}/frontend-${scalajsMode}/*.js`,
      "./index.html",
    ],
    theme: {
      extend: {},
    },
  };
};
