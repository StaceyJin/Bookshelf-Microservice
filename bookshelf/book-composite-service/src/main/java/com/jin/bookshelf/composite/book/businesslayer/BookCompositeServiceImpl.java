package com.jin.bookshelf.composite.book.businesslayer;

import com.jin.api.composite.book.AccountingSummary;
import com.jin.api.composite.book.BookAggregate;
import com.jin.api.composite.book.PortalSummary;
import com.jin.api.composite.book.ServiceAddresses;
import com.jin.api.core.accounting.Accounting;
import com.jin.api.core.book.Book;
import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.composite.book.integrationlayer.BookCompositeIntegration;
import com.jin.utils.exceptions.NotFoundException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.geom.NoninvertibleTransformException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookCompositeServiceImpl implements BookCompositeService{

    public static final Logger LOG = LoggerFactory.getLogger(BookCompositeServiceImpl.class);

    private final BookCompositeIntegration integration;

    private final ServiceUtil serviceUtil;

    public BookCompositeServiceImpl(BookCompositeIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public BookAggregate getBook(int bookId){

        Book book = integration.getBook(bookId);

        if (book == null) throw new NotFoundException("No book found for bookId: " + bookId);

        List<Accounting> accountings = integration.getAccounting(bookId);

        List<Portal> portals = integration.getPortal(bookId);

        return createBookAggregate(book, accountings, portals, serviceUtil.getServiceAddress());
    }

    @Override
    public void createBook(BookAggregate model){

        try{

            LOG.debug("createBookComposite: creates a new composite entity for bookId: {}", model.getBookId());

            Book book =  new Book(model.getBookId(), model.getTitle(), model.getAuthor(), model.getArtist(), model.getSubject(), model.getPublisher(),
                    model.getType(), model.getApp(), model.getStatus(), null);
                    integration.createBook(book);

            if(model.getAccountings() != null){
                model.getAccountings().forEach(r -> {
                    Accounting accounting = new Accounting(model.getBookId(), r.getAccountId(), r.getListedPrice(),
                            r.getPaidPrice(), r.getSavings(), r.getMethod(),null);
                    integration.createAccounting(accounting);
                });

                if(model.getPortals() != null) {
                    model.getPortals().forEach(r -> {
                        Portal portal = new Portal(model.getBookId(), r.getPortalId(), r.getLink(), r.getUserName(), null);
                        integration.createPortal(portal);
                    });
                }
                LOG.debug("createBookComposite: composite entities created for bookId: {}", model.getBookId());
            }

        } catch (RuntimeException ex){
            LOG.warn("createBookComposite failed", ex);
        }
    }

        @Override
        public void deleteBook(int bookId){

            LOG.debug("deleteBookComposite: Deletes a book aggregate for bookId: {}", bookId);
            integration.deleteBook(bookId);
            integration.deleteAccountings(bookId);
            integration.deletePortals(bookId);
            LOG.debug("deleteBookComposite: aggregate entities deleted for bookId: {}", bookId);

        }

    private BookAggregate createBookAggregate(Book book, List<Accounting> accountings, List<Portal> portals, String serviceAddress) {

        int bookId = book.getBookId();
        String title = book.getTitle();
        String author = book.getAuthor();
        String artist = book.getArtist();
        String subject = book.getSubject();
        String publisher = book.getPublisher();
        String type = book.getType();
        String app = book.getApp();
        String status = book.getStatus();

        List<AccountingSummary> accountingSummaries = (accountings == null) ? null :
                accountings.stream()
                .map(r -> new AccountingSummary(r.getAccountId(), r.getListedPrice(), r.getPaidPrice(), r.getSavings(), r.getMethod()))
                .collect(Collectors.toList());

        List<PortalSummary> portalSummaries = (portals == null) ? null :
                portals.stream()
                .map(r -> new PortalSummary(r.getPortalId(), r.getLink(), r.getUserName()))
                .collect(Collectors.toList());

        String bookAddress = book.getServiceAddress();
        String accountingAddress = (accountings != null && accountings.size() > 0)
                ? accountings.get(0).getServiceAddress() : "";
        String portalAddress = (portals != null && portals.size() > 0)
                ? portals.get(0).getServiceAddress() : "";

        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, bookAddress, accountingAddress, portalAddress);

        return new BookAggregate(bookId, title, author, artist, subject, publisher, type, app, status, accountingSummaries, portalSummaries, serviceAddresses);

    }
}
