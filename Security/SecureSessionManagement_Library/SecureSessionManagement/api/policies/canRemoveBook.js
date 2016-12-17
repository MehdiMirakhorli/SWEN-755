module.exports = function(req, res, next) {
    if (req.isAuthenticated()) {
        if(req.user.canRemoveBook == true) {
            return next();
        } else {
            req.addFlash('error', 'You do not have sufficient privilege to remove books!');
            return res.redirect('/');
        }

    }
    else{
        return res.redirect('/login');
    }
};