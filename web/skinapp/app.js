var express = require("express"),
bodyParser = require("body-parser"), 
app = express(); 

app.set("view engine", "ejs"); 
app.use(express.static("public"));
var fs = require('fs');
//====================================================
//               FIREBASE CONFIG

var admin = require("firebase-admin");

var serviceAccount = require("./key.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    storageBucket: "htn2018-93d56.appspot.com",
    databaseURL: "https://htn2018-93d56.firebaseio.com/"
});

var bucket = admin.storage().bucket();

//====================================================


app.get("/", function(req, res){
    
    
    
    res.render("index.ejs"); 
})

app.get("/type/:type", function(req,res){
    var type = req.params.type
    res.render("index.ejs", {name: type})
})













app.listen(process.env.PORT, process.env.IP, function(){
    console.log("Server is listening"); 
})