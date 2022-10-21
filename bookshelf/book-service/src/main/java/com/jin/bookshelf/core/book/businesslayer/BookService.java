package com.jin.bookshelf.core.book.businesslayer;

import com.jin.api.core.book.Book;

public interface BookService {

    public Book getBookById(int bookId);

    public Book createBook(Book model);

    public void deleteBook(int bookId);
}
