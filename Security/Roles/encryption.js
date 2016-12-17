"use strict"

var bcrypt = require('bcrypt');
const saltRounds = 10;

exports.encryptpassword = function(password, callback){
    bcrypt.hash(password, saltRounds, function(error, hash){
        callback(hash);
    })
};

exports.checkpassword = function(password, hash, callback){
    bcrypt.compare(password, hash, function(error, result){
        callback(result);
    })
};
