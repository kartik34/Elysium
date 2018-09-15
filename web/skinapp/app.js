var express = require("express")

var app = express()


app.get("/", function(req, res){
    res.render("index.ejs"); 
})















app.listen(process.env.PORT, process.env.IP, function(){
    console.log("Server is listening"); 
})