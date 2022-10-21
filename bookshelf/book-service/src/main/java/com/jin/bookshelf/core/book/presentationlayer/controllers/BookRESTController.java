package com.jin.bookshelf.core.book.presentationlayer.controllers;

import com.jin.api.core.book.Book;
import com.jin.api.core.book.BookServiceAPI;
import com.jin.bookshelf.core.book.businesslayer.BookService;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.WrongGenreException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRESTController implements BookServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(BookRESTController.class);

    private final ServiceUtil serviceUtil;

    private final BookService bookService;

    public BookRESTController(ServiceUtil serviceUtil, BookService bookService) {
        this.serviceUtil = serviceUtil;
        this.bookService = bookService;
    }

    @Override
    public Book getBook(int bookId){

        LOG.debug("/book MS returns the found book for bookId: " + bookId);

        if (bookId < 1) throw new InvalidInputException("Invalid bookId: " + bookId);
        //if (bookId == 13) throw new NotFoundException("No book found for bookId: " + bookId);
        if (bookId == 101) throw new WrongGenreException("Genre not recognized for bookId: " + bookId);

        //return new Book(bookId, "title 123", "author 123", "artist 123", "subject 123", "publisher 123", "type 123", "app 123", "status 123", serviceUtil.getServiceAddress());
        Book book = bookService.getBookById(bookId);
        return book;
    }

    @Override
    public Book createBook(Book model){
        Book book = bookService.createBook(model);

        LOG.debug("REST createBook: entity created for bookId: {}", book.getBookId());

        return book;
    }

    @Override
    public void deleteBook(int bookId){
        LOG.debug("REST deleteBook: tries to delete entity for bookId: {}", bookId);

        bookService.deleteBook(bookId);
    }

}
