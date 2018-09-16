var express     = require("express"),
    bodyParser  = require("body-parser"), 
    firebase    = require("firebase"),
    methodOverride = require("method-override"), 
    mongoose    = require("mongoose"), 
    app         = express(); 
    
//=================================================================
//             CONNECTING LIBRARIES

mongoose.connect("mongodb://localhost/test2"); 
app.use(bodyParser.urlencoded({extended: true})); 
app.set("view engine", "ejs"); 
var serveStatic = require('serve-static')
app.use(methodOverride("_method"));
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
     if(files != null && files != ""){
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
                            Mole.create({
                                path: files[0].name,
                                malignancy: doc.data().risk,
                                date: doc.data().date,
                                basicId: doc.data().id,
                                malignancyRisk: doc.data().risk_value
                            }, function(err, mole){
                                if(err){
                                    console.log(err)
                                }else{
                                   
                                   Mole.find({}, function(err, moles){
                                       if(err){
                                           console.log(err)
                                       }else{
                                           console.log(moles); 
                                           res.render("index", {moles: moles})
                                       }
                                   })
                                  
            
                                }
                            })
                            console.log('Document data:', doc.data().risk);
                        } else {
                            console.log('No such document');
                        }
                    })
                    .catch(err => {
                      console.log('Error getting document', err);
                    });
                
                

            }
            files[0].delete(function(err){
                if(err){
                    console.log(err)
                }
            })
  
        });
            
         
     }else{
         Mole.find({}, function(err, moles){
           if(err){
               console.log(err)
           }else{
               console.log(moles); 
               res.render("index", {moles: moles})
           }
       })
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
            console.log(foundMole)
            res.render("show", {mole: foundMole})
        }
    })
})
app.put("/showpage/:id", function(req,res){
    
    
    
    
    Mole.findByIdAndUpdate(req.params.id, req.body.message, function(err, updatedMole){
        if(err){
            res.redirect("index")
        }else{
            console.log(updatedMole)
            var id = updatedMole.basicId.toString()
            var string = "message"
        
            
          admin.database().ref("/").update({[updatedMole.basicId] : req.body.message});


          
          res.redirect(updatedMole._id)

         
            
        }
    })
})

app.post("/showpage", function(req,res){
    //edit mole db to include new message 
    
})











app.listen(process.env.PORT, process.env.IP, function(){
    console.log("Server is listening"); 
})