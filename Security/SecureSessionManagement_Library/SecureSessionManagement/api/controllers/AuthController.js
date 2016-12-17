

var passport = require('passport');

module.exports = {

    _config: {
        actions: false,
        shortcuts: false,
        rest: false
    },

    login: function(req, res) {

        if (req.method == "GET") {
            return res.view('User/login');
        }

        passport.authenticate('local', function(err, user, info) {
            if ((err) || (!user)) {
                req.addFlash('error', info.message);
                return res.redirect('/login');
            }
            req.logIn(user, function(err) {
                if (err) res.send(err);
                //req.session.cookie.expires = false;
                req.addFlash('success', 'You are logged in!');
                return res.redirect('/');
            });

        })(req, res);
    },

    logout: function(req, res) {
        req.logout();
        req.addFlash('success', 'You are logged out!');
        res.redirect('/');
    }
};
