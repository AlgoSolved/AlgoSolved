const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  if (process.env.NODE_ENV === "development") {
    app.use(
      createProxyMiddleware("/api", {
        target: process.env.REACT_APP_API_BASE_URL,
        changeOrigin: true,
      }),
    );
  }
};
