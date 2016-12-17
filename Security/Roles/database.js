"use strict"

var Sequelize = require('sequelize'),
    encryption = require('./encryption'),
    sequelize = new Sequelize("sqlite://users.db", {logging: false});

//definition of model and DB structure
var User = sequelize.define('user', {
    username: {
        type: Sequelize.STRING,
        unique: true,
        allowNull: false
    },
    password: {
        type: Sequelize.STRING,
        allowNull: false
    },
    roleid: {
        type: Sequelize.STRING,
        allowNull: false
    },
    sessionid: {
        type: Sequelize.STRING
    }
});

exports.User = User;

//initialize the DB with users
exports.init = function(){
    User.sync({force: true}).then(function(){
        console.log("User table created");
        //encrypt password
        encryption.encryptpassword("12345", function(result){
            //create admin user with username admin, password 12345
            User.create({username: "admin", password: result, roleid: "ADM"}).then(function(){
                console.log("User admin created");
            });
        });
        //encrypt password
        encryption.encryptpassword("09876", function(result){
            //create supervisor user with username super, password 09876
            User.create({username: "super", password: result, roleid: "SUP"}).then(function(){
                console.log("User supervisor created");
            })
        });
    }).catch(function(error){
        console.log(error);
    });
};
