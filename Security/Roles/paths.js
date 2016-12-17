var DB = require('./database'),
    encryption = require('./encryption');

//1 minute, time in miliseconds
//change first value to modify time in seconds
const timeInSeconds = 60;

var checkexpiration = function(request, callback){

    var maxSessionTime = timeInSeconds * 1000;
    var session = request.session;
    //current time minus time of last activity
    var difference = Date.now() - session.time;
    if(difference < maxSessionTime){
        //the session hasn't expired, less than a minute
        session.time = Date.now();
        callback(false);
    } else {
        //the session expired. more than a minute
        //the user has been inactive for more than a minute
        callback(true);
    }
};

var checksession = function(request, callback){
    //checks for the user in session and if the sessionid matches the one in the
    //db. If everyting goes well, returns true, otherwise expires the session,
    //remove the sessionid from the db and return false
    var session = request.session;
    DB.User.findOne({where: {username: session.username}}).then(function(user){
        if(user){
            //ensures sessionid and roleid matches
            if(user.sessionid == session.id && user.roleid == session.roleid){
                //username in session founf in db. matches session id and roleid
                callback(true);
            } else {
                //username in session found in db but either doesn't match sessionid
                //or roleid
                user.sessionid = null;
                user.save().then(function(){
                    session.destroy(function(error){
                        if(!error){
                            callback(false);
                        }
                    });
                });
            }
        } else {
            //username is in session but it's not found in the db
            session.destroy(function(error){
                if(!error){
                    callback(false);
                }
            });
        }
    });
}

exports.index = function(request, response, next){
    "use strict";

    var session = request.session;

    if(session.time){
        //first check user inactivity time
        checkexpiration(request, function(result){
            if(result){
                //inactive for too long. logout to destroy the session
                response.redirect('/logout');
            } else {
                //still active. check for username in session
                if(session.username){
                    //check for session info
                    checksession(request, function(result){
                        if(result){
                            //everything well
                            response.redirect("/dashboard");
                        } else {
                            //something wrong, ask to login
                            response.render('login');
                        }
                    });
                } else {
                    //no username found, login
                    response.render('login');
                }
            }
        });
    } else {
        //no last activity time found
        response.render('login');
    }
}

exports.login = function(request, response, next){

    //this process can be improved by removing spaces from username
    //plus by lowercasing the username, but that'd have to be discussed with the
    //customer

    DB.User.findOne({where: {username: request.body.username}}).then(function(user){
        if(user){
            //we found the user in the database
            //ensure passwords match
            encryption.checkpassword(request.body.password, user.password, function(result){
                if(result){
                    var session = request.session;
                    //set sessionid to user then save in the database
                    user.sessionid = session.id;
                    user.save().then(function(){
                        //save information in session
                        session.username = user.username;
                        session.roleid = user.roleid;
                        session.time = Date.now();
                        response.redirect("/dashboard");
                    });
                } else {
                    //passwords do not match
                    response.render('login', {error: "Wrong credentials"});
                }
            });
        } else {
            //user not found
            //do something else with the user. Redirect to some other page or ...
            response.render('login', {error: "Wrong credentials"});
        }
    }).catch(function(error){
        response.render('login', {error: "User could not have been retrieved"});
    });
};

exports.dashboard = function(request, response){
    "use strict";

    var session = request.session;

    if(session.time){
        //first check user inactivity time
        checkexpiration(request, function(result){
            if(result){
                //inactive for too long. logout to destroy the session
                response.redirect('/logout');
            } else {
                //still active. check for username in session
                if(session.username){
                    //check for session info
                    checksession(request, function(result){
                        if(result){
                            //everything well
                            response.render('dashboard', {'role': session.roleid});
                        } else {
                            //something wrong, ask to login
                            response.redirect("/");
                        }
                    });
                } else {
                    //no username found, login
                    response.redirect("/");
                }
            }
        });
    } else {
        //no last activity time found
        response.redirect("/");
    }
};

exports.admin_info = function(request, response){
    "use strict";

    var session = request.session;

    if(session.time){
        //first check user inactivity time
        checkexpiration(request, function(result){
            if(result){
                //inactive for too long. logout to destroy the session
                response.redirect('/logout');
            } else {
                //still active. check for username in session
                if(session.username){
                    //check for session info
                    checksession(request, function(result){
                        if(result){
                            //check por permissions for the requested page
                            if(session.roleid == "ADM"){
                                //everything well
                                response.render('admin_info');
                            } else {
                                //no permission to access this resource. take to
                                //dashboard
                                response.redirect("/dashboard");
                            }
                        } else {
                            //something wrong, ask to login
                            response.redirect("/");
                        }
                    });
                } else {
                    //no username found, login
                    response.redirect("/");
                }
            }
        });
    } else {
        //no last activity time found
        response.redirect("/");
    }
};

exports.admin_supervisor_info = function(request, response){
    "use strict";

    var session = request.session;

    if(session.time){
        //first check user inactivity time
        checkexpiration(request, function(result){
            if(result){
                //inactive for too long. logout to destroy the session
                response.redirect('/logout');
            } else {
                //still active. check for username in session
                if(session.username){
                    //check for session info
                    checksession(request, function(result){
                        if(result){
                            //check por permissions for the requested page
                            if(['ADM', 'SUP'].indexOf(session.roleid)>=0){
                                //everything well
                                response.render('admin_supervisor_info');
                            } else {
                                //no permission to access this resource. take to
                                //dashboard
                                response.redirect("/dashboard");
                            }
                        } else {
                            //something wrong, ask to login
                            response.redirect("/");
                        }
                    });
                } else {
                    //no username found, login
                    response.redirect("/");
                }
            }
        });
    } else {
        //no last activity time found
        response.redirect("/");
    }
};

exports.logout = function(request, response, next){
    "use strict";

    var session = request.session;
    //find username in session
    DB.User.findOne({where: {username: session.username}}).then(function(user){
        if(user){
            //username found. delete sessionid from db
            user.sessionid = null;
            user.save().then(function(){ });
        }
        //destroy session and take to login
        session.destroy(function(error){ });
        response.redirect("/");
    }).catch(function(error){
        error.message = "User could not have been retrieved";
        next(error);
    });
};
