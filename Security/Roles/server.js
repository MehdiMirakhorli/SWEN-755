"use strict"

var express = require('express'),
    bodyParser = require('body-parser'),
    nunjucks = require('nunjucks'),
    session = require('express-session'),
    paths = require('./paths'),
    app = express();

nunjucks.configure('views',{
    autoscape: true,
    express: app
})

app.set('view engine', 'html');
app.set('views', __dirname + '/views');
app.use('/static', express.static(__dirname + '/static'));
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(session({
    secret: process.env.SECRET,
    resave: false,
    saveUninitialized: true,
    cookie: { maxAge: false, expires: false } //makes cookie expire after closing the browser
}));

function errorHandler(error, request, response, next){
    console.error(error.message);
    console.error(error.stack);
    response.status(500);
    response.render('error_template', {error: error.message});
}

//definition of gets and posts allowed
app.post('/login', paths.login, paths.dashboard);
app.get('/', paths.index, paths.dashboard);
app.get('/dashboard', paths.dashboard);
app.get('/admin_info', paths.admin_info);
app.get('/admin_supervisor_info', paths.admin_supervisor_info);
app.get('/logout', paths.logout);

app.use(errorHandler);

var server = app.listen(7760, function(){
    var port = server.address().port;
    console.log('Express server listening on port %s', port);
});
