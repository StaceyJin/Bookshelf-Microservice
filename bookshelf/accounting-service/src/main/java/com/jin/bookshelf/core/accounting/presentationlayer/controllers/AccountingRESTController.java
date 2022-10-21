package com.jin.bookshelf.core.accounting.presentationlayer.controllers;

import com.jin.api.core.accounting.Accounting;
import com.jin.api.core.accounting.AccountingServiceAPI;
import com.jin.bookshelf.core.accounting.businesslayer.AccountingService;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.WrongGenreException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountingRESTController implements AccountingServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(AccountingRESTController.class);

    private final AccountingService accountingService;

    @Autowired
    public AccountingRESTController(AccountingService accountingService) {
        this.accountingService = accountingService; }

    @Override
    public List<Accounting> getAccounting(int bookId){

        if (bookId < 1) throw new InvalidInputException("Invalid bookId: " + bookId);
/*        if (bookId == 113){
            LOG.debug("No accounting found for bookId: {}", + bookId);
            return new ArrayList<>();
        }*/
        if (bookId == 201) throw new WrongGenreException("Genre not recognized for bookId: " + bookId);

        /*List<Accounting> listAccounting = new ArrayList<>();
        listAccounting.add(new Accounting(bookId, 1, 9.99, 4.99, 5.00, "paypal", serviceUtil.getServiceAddress()));*/

        List<Accounting> listAccounting = accountingService.getBookById(bookId);

        LOG.debug("/accounting found response size: {}", listAccounting.size());

        return listAccounting;
    }

    @Override
    public Accounting createAccounting(Accounting model){

        Accounting accounting = accountingService.createAccounting(model);

        LOG.debug("REST Controller createAccounting: create an entity: {}/{}", accounting.getBookId(), accounting.getAccountId());
        return  accounting;
    }

    @Override
    public void deleteAccountings(int bookId){

        LOG.debug("REST Controller deleteAccountDetails: trying to delete accountDetails for the book with bookId: {}", bookId);

        accountingService.deleteAccountDetails(bookId);
    }

}
