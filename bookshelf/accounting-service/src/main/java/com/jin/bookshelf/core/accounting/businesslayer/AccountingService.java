package com.jin.bookshelf.core.accounting.businesslayer;

import com.jin.api.core.accounting.Accounting;

import java.util.List;

public interface AccountingService {

    public List<Accounting> getBookById(int bookId);

    public Accounting createAccounting(Accounting model);

    public void deleteAccountDetails(int bookId);
}
