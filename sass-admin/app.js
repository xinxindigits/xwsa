// const fs = require("fs");
const path = require("path");
const express = require("express");

const cors = require("cors");

var { createProxyMiddleware } = require("http-proxy-middleware");
const app = express();
var proxyPath = "http://localhost:8081";
var proxyOption = { target: proxyPath };
var exampleProxy = createProxyMiddleware(proxyOption);
app.use(express.static(path.resolve(__dirname, "./dist")));
app.use("/sass", exampleProxy);
app.use(
  cors({
    origin: true,
    methods: ["POST"]
  })
);
app.all("*", function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Content-Type");
  res.header("Access-Control-Allow-Methods", "*");
  res.header("Content-Type", "application/json;charset=utf-8");
  next();
});
app.listen(9633, () => {
  console.log("sass-admin listening 9633");
});
