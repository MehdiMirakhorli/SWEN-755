/**
 * UserController
 *
 * @description :: Server-side logic for managing users
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

var passport = require('passport');
var validator = require('validator');

module.exports = {


  _config: {
    actions: false,
    shortcuts: false,
    rest: false
  },

  index: function (req, res) {
    User.find(function(err, users) {
      if (err) {return res.serverError(err);}
      return res.view({users: users ,layout: 'layout'});
    });
  },


  signup: function (req, res) {

    if(req.method == "GET") {
      return res.view('User/signup');
    }

    var email = req.param("email");
    var password = req.param("password");

    if (!email || !password) {
      return res.view({errorMessage: 'Please provide email and password'});
    }

    if(!validator.isEmail(email)) {
      return res.view({errorMessage: 'Please provide a valid email'});
    } else if (password.length < 6) {
      return res.view({errorMessage: 'Password should be atleast 6 letters!'});
    }

    User.findOne({email:email}).exec(function (err, user){
      if (err) {
        return res.serverError(err);
      } else if (user) {
        return res.view({name: name, author: author, errorMessage: 'Email already exists!'});
      } else {

        // Create a user record in the database.
        User.create({
          email: email,
          password: password,
        }).exec(function (err, newUser) {
          // If there was an error, we negotiate it.
          if (err) {

            // If this is NOT a waterline validation error, it is a mysterious error indeed.
            var isWLValidationErr = _.isObject(err) && _.isObject(err.invalidAttributes);
            if (!isWLValidationErr) {
              return res.serverError(err);
            }
            return res.badRequest(err);

          }//</if (err)>

          req.addFlash('success', 'User created successfully!');
          return res.redirect('/login');

        });//</User.create>

      }


    });



  },//</UserController.signup>


  updatePriviledge: function (req, res) {

    if(req.method == "POST") {
      var id = req.param("id");
      if (id) {
        User.findOne({id: id}).exec(function(err, user) {
          if (err) {return res.serverError(err);}
          if (!user) {return res.badRequest(err);}

          user.canAddBook = req.param("canAddBook");
          user.canEditBook = req.param("canEditBook");
          user.canRemoveBook = req.param("canRemoveBook");
          user.save(function(error) {
            if(error) {
              return res.serverError(err);
            } else {
              return res.send({successMessage: 'User updated successfully!'});
            }
          });
        });
      }
    }
  }

};

