module.exports = function(req, res, next) {
    if (req.isAuthenticated()) {
        if(req.user.canAddBook == true) {
            return next();
        } else {
            req.addFlash('error', 'You do not have sufficient privilege to add books!');
            return res.redirect('/');
        }

    }
    else{
        return res.redirect('/login');
    }
};