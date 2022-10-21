package com.jin.bookshelf.composite.book.presentationlayer.controllers;

import com.jin.api.composite.book.*;
import com.jin.api.core.accounting.Accounting;
import com.jin.api.core.book.Book;
import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.composite.book.businesslayer.BookCompositeService;
import com.jin.bookshelf.composite.book.integrationlayer.BookCompositeIntegration;
import com.jin.utils.exceptions.NotFoundException;
import com.jin.utils.exceptions.WrongGenreException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class BookCompositeRESTController implements BookCompositeServiceAPI {

    private final BookCompositeIntegration integration;
    private final BookCompositeService bookCompositeService;

    private static final Logger LOG = LoggerFactory.getLogger(BookCompositeRESTController.class);

    public BookCompositeRESTController(BookCompositeIntegration integration, BookCompositeService bookCompositeService) {
        this.integration = integration;
        this.bookCompositeService = bookCompositeService;
    }

    @Override
    public BookAggregate getCompositeBook(int bookId){

/*        Book book = integration.getBook(bookId);
        if (book == null) throw new NotFoundException("No book found for bookId: " + bookId);
        if (bookId == 101) throw new WrongGenreException("Genre not recognized for bookId: " + bookId);

        List<Accounting> accounting = integration.getAccounting(bookId);

        List<Portal> portal = integration.getPortal(bookId);

        return createBookAggregate(book, accounting, portal, serviceUtil.getServiceAddress());*/

        LOG.debug("BookComposite received getBookComposite request.");

        BookAggregate bookAggregate = bookCompositeService.getBook(bookId);

        return bookAggregate;

        }

        @Override
        public void createCompositeBook(BookAggregate model){

            LOG.debug("BookComposite received createBookComposite request.");

            bookCompositeService.createBook(model);
        }

        @Override
        public void deleteCompositeBook(int bookId){

            LOG.debug("BookComposite received deleteBookComposite request.");
            bookCompositeService.deleteBook(bookId);
        }

    }


