module.exports = function(req, res, next) {
  if (req.isAuthenticated()) {
    if(req.user.isAdmin == true) {
      return next();
    } else {
      req.addFlash('error', 'You do not have sufficient privilege to see Users!');
      return res.redirect('/');
    }

  }
  else{
    return res.redirect('/login');
  }
};
