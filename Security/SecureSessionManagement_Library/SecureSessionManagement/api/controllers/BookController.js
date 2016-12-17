/**
 * BookController
 *
 * @description :: Server-side logic for managing Books
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	index: function (req, res) {
        Book.find(function(err, books) {
            if (err) {return res.serverError(err);}
            return res.view({books: books ,layout: 'layout'});
        });
    },

    insert: function (req, res) {

        if (req.method == "GET") {
            return res.view();
        }

        var name = req.param("name");
        var author = req.param("author");

        if (!name || !author) {
            return res.view({errorMessage: 'Please provide all values'});
        }

        Book.findOne({name:name}).exec(function (err, bookName){
            if (err) {
                return res.serverError(err);
            } else if (bookName) {
                return res.view({name: name, author: author, errorMessage: 'Book already exists!'});
            } else {
                Book.create({name: name, author: author}).exec(function (err, book){
                    if (err) { return res.serverError(err); }
                    return res.view({successMessage: 'Book added successfully!'});
                });
            }


        });
    },

    update: function (req, res) {


        var id = req.param("id");

        if (!id) {
            return res.view({notFound:true, errorMessage: 'Please select a book'});
        }

        // Make sure the book exist
        Book.findOne({id:id}).exec(function (err, book){
            if (err) {
                return res.serverError(err);
            } if (!book) {
                return res.view({notFound:true, errorMessage: 'Please select a book'});
            }

            if (req.method == "GET") {
                return res.view({book: book, layout: 'layout'});

            }

            var name = req.param("name");
            var author = req.param("author");

            if (!name || !author) {
                book.name = (name) ? name: '';
                book.author = (author) ? author: '';
                return res.view({book: book, errorMessage: 'Please provide all values!', layout: 'layout'});
            }

            // check if other books with same name does not exist
            Book.findOne({name:name}).exec(function (err, checkBookExist){
                if (err) {
                    return res.serverError(err);
                } else if (checkBookExist) {
                    if (checkBookExist.id != book.id) {
                        book.name = name;
                        book.author = author;
                        return res.view({book: book, errorMessage: 'Another book with this name already exist!', layout: 'layout'});
                    }
                }

                book.name = name;
                book.author = author;
                book.save(function(error) {
                    if(error) {
                        return res.serverError(err);
                    } else {
                        return res.view({book: book, successMessage: 'Book updated successfully!'});
                    }
                });

            });

        });

    }


};

