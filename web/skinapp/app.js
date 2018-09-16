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
var serveStatic = require('serve-static')

app.use(serveStatic('skinapp/'))
app.use(express.static("public"));
var fs = require('fs');

//=================================================================
//                MONGODB CONFIG
 

var moleSchema = new mongoose.Schema({
    path: String, 
    malignancy: Number,
    malignancyRisk: String,
    date: String,
    basicId: Number,
    message: String
    
})

var Mole = mongoose.model("Mole", moleSchema); 


//==================================================================
//               FIREBASE CONFIG



var admin = require("firebase-admin");


var serviceAccount = require("./key.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    authDomain: "htn2018-93d56.firebaseapp.com",
    databaseURL: "https://htn2018-93d56.firebaseio.com",
    projectId: "htn2018-93d56",
    storageBucket: "htn2018-93d56.appspot.com",
    messagingSenderId: "941946207765"
});
var bucket = admin.storage().bucket();

var db = admin.firestore();



//===================================================================
//                ROUTES

app.get("/", function(req, res){
    
    
    bucket.getFiles() 
    .then(results => {
     const files = results[0];
     if(files != null || files != ""){
         files[0].download({ destination: './public/photos/'+files[0].name }, function(err) {
         
       
            if(err){
                console.log(err)
            }
            else{
                console.log("hello");
                console.log(files[0].name)
                var moleRef = db.collection('moles')
                var latestMole = moleRef.orderBy('id', 'desc').limit(1);
                
                latestMole.get()
                    .then(querySnapshot => {
                        const doc = querySnapshot.docs[0];
                        if (doc) {
                            console.log('Document data:', doc.data());
                        } else {
                            console.log('No such document');
                        }
                    })
                    /*.then(doc => {
                      if (!doc.exists) {
                        console.log('No such document!');
                      } else {
                        console.log('Document data:', doc.data());
                      }
                    })*/
                    .catch(err => {
                      console.log('Error getting document', err);
                    });
                
                Mole.create({
                    path: files[0].name,
                    malignancy: 0.1,
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
            
         
     }else{
         res.render("landing")
     }
     
    })
    .catch(err => {
      console.error('ERROR:', err);
    });
    
    
})

app.get("/showpage/:id", function(req,res){
    Mole.findById(req.params.id, function(err, foundMole){
        if(err){
            res.redirect("index")
        }else{
            res.render("show", {mole: foundMole})
        }
    })
    res.render("show")
})

app.post("/showpage", function(req,res){
    //edit mole db to include new message 
    
})











app.listen(process.env.PORT, process.env.IP, function(){
    console.log("Server is listening"); 
})