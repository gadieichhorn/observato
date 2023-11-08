require('dotenv').config();

const express = require("express");
const morgan = require("morgan");
const {createProxyMiddleware} = require("http-proxy-middleware");
const https = require("https");
const {json} = require("express");

let token = '';
const data = {
    'username': process.env.TLOGIN,
    'password': process.env.TPASSWORD,
    'grant_type': process.env.TGRANTTYPE,
    'client_id': process.env.TCLIENT,
    'client_secret': process.env.TSECRET
};
const postData = Object.keys(data).map(function (key) {
    return encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
}).join('&');
const options = {
    hostname: 'test-observato.com',
    port: 443,
    path: '/token',
    method: 'POST',
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': postData.length
    }
};

function renewToken() {

    let text = ''

    const req = https.request(options, (res) => {
        res.setEncoding('utf8');
        res.on('data', (chunk) => {
            text = text.concat(chunk)
        });
        res.on('end', () => {
            console.log('No more data in response.');
            token = JSON.parse(text)['access_token'];
        });
    });

    req.on('error', (e) => {
        console.error(`problem with request: ${e.message}`);
    });  // do something here

    // Write data to request body
    req.write(postData);
    req.end();

}

renewToken();
setInterval(renewToken, 1000 * 60 * 30);

// Create Express Server
const app = express();

// Configuration
const PORT = 3000;
const HOST = "localhost";

// Logging
app.use(morgan("dev"));

// Authorization
app.use("", (req, res, next) => {
    // req.headers["x-auth-request-access-token"] = token;
    req.headers["Authorization"] = 'Bearer ' + token;
    next();
});

const observato = "http://localhost:8080"

app.use("/api", createProxyMiddleware({
    target: observato, changeOrigin: true,
}));

// Start the Proxy
app.listen(PORT, HOST, () => {
    console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
