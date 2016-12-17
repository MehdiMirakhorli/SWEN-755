/**
 * UtilityController
 *
 * @description :: Server-side logic for managing utilities
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	


  /**
   * `UtilityController.removeuser()`
   */
  remove: function (req, res) {

    var id = req.param("id");

    if (!id) {
      req.addFlash('error', 'You have to select a book!');
      return res.redirect('/book');
    }

    // Make sure the book exist
    Book.findOne({id:id}).exec(function (err, book){
      if (err) {
        return res.serverError(err);
      }
      if (!book) {
        req.addFlash('error', 'The book is not found!');
        return res.redirect('/book');
      } else {

        Book.destroy({
          id: id
        }).exec(function (err){
          if (err) {
            return res.negotiate(err);
          }
          req.addFlash('success', 'The book removed successfully!');
          return res.redirect('/book');
        });

      }

    });
  }
};

