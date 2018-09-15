var express = require("express"),
bodyParser  = require("body-parser"), 
firebase    = require("firebase"),
app         = express(); 

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
    
    bucket.getFiles() 
    .then(results => {
     const files = results[0];
     files[0].download({ destination: './photos/'+files[0].name }, function(err) {
            if(err){
                console.log(err)
            }
            else{
                console.log("hello");
                console.log(files[0].name)
            }
  
        });
            
    })
    .catch(err => {
      console.error('ERROR:', err);
    });
    
    
})

app.get("/type/:type", function(req,res){
    var type = req.params.type
    res.render("index.ejs", {name: type})
})













app.listen(process.env.PORT, process.env.IP, function(){
    console.log("Server is listening"); 
})