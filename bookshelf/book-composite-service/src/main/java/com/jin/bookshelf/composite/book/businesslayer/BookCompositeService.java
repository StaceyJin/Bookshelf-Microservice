package com.jin.bookshelf.composite.book.businesslayer;

import com.jin.api.composite.book.BookAggregate;

public interface BookCompositeService {

    public BookAggregate getBook(int bookId);

    public void createBook(BookAggregate bookAggregate);

    public void deleteBook(int bookId);
}
