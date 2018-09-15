var express     = require("express"),
    bodyParser  = require("body-parser"), 
    firebase    = require("firebase"),
    mongoose    = require("mongoose"), 
    app         = express(); 
    
//=================================================================
//             CONNECTING LIBRARIES

mongoose.connect("mongodb://localhost/restful_blog_app"); 
app.use(bodyParser.urlencoded({extended: true})); 
app.set("view engine", "ejs"); 
app.use(express.static("public"));
var fs = require('fs');

//=================================================================
//                MONGODB CONFIG
 

var moleSchema = new mongoose.Schema({
    path: String, 
    malignancy: Number,
    date: String
    
})

var Mole = mongoose.model("Mole", moleSchema); 


//==================================================================
//               FIREBASE CONFIG

var admin = require("firebase-admin");

var serviceAccount = require("./key.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    storageBucket: "htn2018-93d56.appspot.com",
    databaseURL: "https://htn2018-93d56.firebaseio.com/"
});

var bucket = admin.storage().bucket();

//===================================================================
//                ROUTES

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
                Mole.create({
                    path: files[0].name,
                    malignancy: 0.99, 
                    date: "Sep 15"
                }, function(err, mole){
                    if(err){
                        console.log(err)
                    }else{
                      res.render("index", {mole: mole})

                    }
                })

            }
            files[0].delete(function(err){
                if(err){
                    console.log(err)
                }
            })
  
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