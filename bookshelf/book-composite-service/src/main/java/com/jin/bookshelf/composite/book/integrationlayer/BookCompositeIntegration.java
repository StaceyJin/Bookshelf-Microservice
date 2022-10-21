package com.jin.bookshelf.composite.book.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jin.api.core.accounting.Accounting;
import com.jin.api.core.accounting.AccountingServiceAPI;
import com.jin.api.core.book.Book;
import com.jin.api.core.book.BookServiceAPI;
import com.jin.api.core.portal.Portal;
import com.jin.api.core.portal.PortalServiceAPI;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.NotFoundException;
import com.jin.utils.exceptions.WrongGenreException;
import com.jin.utils.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookCompositeIntegration implements BookServiceAPI, AccountingServiceAPI, PortalServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(BookCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String bookServiceUrl;
    private final String accountingServiceUrl;
    private final String portalServiceUrl;

    public BookCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.book-service.host}") String bookServiceHost,
            @Value("${app.book-service.port}") String bookServicePort,

            @Value("${app.accounting-service.host}") String accountingServiceHost,
            @Value("${app.accounting-service.port}") String accountingServicePort,

            @Value("${app.portal-service.host}") String portalServiceHost,
            @Value("${app.portal-service.port}") String portalServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        bookServiceUrl = "http://" + bookServiceHost + ":" + bookServicePort + "/book/";
            /*accountingServiceUrl = "http://" + accountingServiceHost + ":" + accountingServicePort + "/accounting?bookId=";
            portalServiceUrl = "http://" + portalServiceHost + ":" + portalServicePort + "/portal?bookId=";*/
        accountingServiceUrl = "http://" + accountingServiceHost + ":" + accountingServicePort + "/accounting";
        portalServiceUrl = "http://" + portalServiceHost + ":" + portalServicePort + "/portal";
    }

    @Override
    public Book getBook(int bookId) {

        try {
            String url = bookServiceUrl + bookId;
            LOG.debug("Will call getBook API on URL: {}", url);

            Book book = restTemplate.getForObject(url, Book.class);
            LOG.debug("Found a book with id: {}", book.getBookId());

            return book;
        } catch (HttpClientErrorException ex) {

            throw handleHttpClientException(ex);

/*            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(ex));

                case NOT_ACCEPTABLE:
                    throw new WrongGenreException(getErrorMessage(ex));

                default:
                    LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;*/
        }

    }

    @Override
    public Book createBook(Book model) { //send POST request to book service

        try {
            String url = bookServiceUrl;
            return restTemplate.postForObject(bookServiceUrl, model, Book.class);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }

    @Override
    public void deleteBook(int bookId) {

        try {
            String url = bookServiceUrl + "/" + bookId;
            LOG.debug("Will call the deleteBook API on URL: {}", url);

            restTemplate.delete(url);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }

    private String getErrorMessage(HttpClientErrorException ex) {

        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

    @Override
    public List<Accounting> getAccounting(int bookId) {
        try {
            String url = accountingServiceUrl + "?bookId=" + bookId;

            LOG.debug("Will call getAccounting API on URL: {}", url);
            List<Accounting> accounting = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Accounting>>() {
                    }).getBody();

            LOG.debug("Found {} accounting record for a book with id: {}", accounting.size(), bookId);
            return accounting;
        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting accounting, return no accounting: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Accounting createAccounting(Accounting model) {

        try {
            //need /recommendation
            String url = accountingServiceUrl;
            LOG.debug("Will post a new accounting to URL: {}", url);

            Accounting accounting = restTemplate.postForObject(url, model, Accounting.class);
            LOG.debug("Created an accounting for bookId: {}, accountId: {}", accounting.getBookId(), accounting.getAccountId());

            return accounting;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }


    }

    @Override
    public void deleteAccountings(int bookId) {

        try {

            String url = accountingServiceUrl + "?bookId=" + bookId;
            LOG.debug("Will call deleteAccountings API on URL: {}", url);

            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }

    @Override
    public List<Portal> getPortal(int bookId) {
        try {
            String url = portalServiceUrl + "?bookId=" + bookId;

            LOG.debug("Will call getPortal API on URL: {}", url);
            List<Portal> portal = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Portal>>() {
                    }).getBody();

            LOG.debug("Found {} portal for a book with id: {}", portal.size(), bookId);
            return portal;
        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting portal, return no portal: {}", ex.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    public Portal createPortal(Portal model) {

        try {
            //need /review
            String url = portalServiceUrl;
            LOG.debug("Will post a new portal to URL: {}", url);

            Portal portal = restTemplate.postForObject(url, model, Portal.class);
            LOG.debug("Created a portal for bookId: {}, portalId: {}", portal.getBookId(), portal.getPortalId());

            return portal;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public void deletePortals(int bookId) {

        try {
            //need /review?productId=productId
            String url = portalServiceUrl + "?bookId=" + bookId;
            LOG.debug("Will send delete request to API on URL: {}", url);
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        switch (ex.getStatusCode()) {

            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(ex));

            case UNPROCESSABLE_ENTITY:
                return new InvalidInputException(getErrorMessage(ex));

            case NOT_ACCEPTABLE:
                return new WrongGenreException(getErrorMessage(ex));

            default:
                LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                LOG.warn("Error body: {}", ex.getResponseBodyAsString());

                return ex;
        }
    }
}
