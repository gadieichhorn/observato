require('dotenv').config();

const express = require("express");
const morgan = require("morgan");
const {createProxyMiddleware} = require("http-proxy-middleware");
const https = require("https");
const {json} = require("express");

let token = 'D43A9CA45A0D9CA831E3E212E69C019A';

const app = express();

const PORT = 3000;
const HOST = "localhost";

app.use(morgan("dev"));

app.use("", (req, res, next) => {
    req.headers["Authorization"] = token;
    next();
});

const observato = "http://localhost:8080"

app.use("/", createProxyMiddleware({
    target: observato, changeOrigin: true,
}));

app.listen(PORT, HOST, () => {
    console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
