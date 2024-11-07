import { Application } from "express";
import { createProxyMiddleware } from "http-proxy-middleware";

module.exports = function (app: Application) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "http://localhost:8080", // 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
    })
  );
};
