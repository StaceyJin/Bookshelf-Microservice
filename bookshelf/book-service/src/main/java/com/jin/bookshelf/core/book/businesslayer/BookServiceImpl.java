package com.jin.bookshelf.core.book.businesslayer;

import com.jin.api.core.book.Book;
import com.jin.bookshelf.core.book.datalayer.BookEntity;
import com.jin.bookshelf.core.book.datalayer.BookRepository;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.NotFoundException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;

    private final BookMapper mapper;

    private final ServiceUtil serviceUtil;

    public BookServiceImpl(BookRepository repository, BookMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Book getBookById(int bookId){
        BookEntity entity = repository.findByBookId(bookId)
                .orElseThrow(() -> new NotFoundException("No book found for bookId: " + bookId));

        Book response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());

        LOG.debug("Book getBookById: found bookId: {}", response.getBookId());
        return response;
    }

    @Override
    public Book createBook(Book model){
        try{
            BookEntity entity = mapper.modelToEntity(model);
            BookEntity newEntity = repository.save(entity);

            LOG.debug("createBook: entity created for bookId: {}", model.getBookId());
            return mapper.entityToModel(newEntity);
        } catch(DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate key, bookId: ", model.getBookId());
        }
    }

    @Override
    public void deleteBook(int bookId){

        LOG.debug("deleteBook: try to delete entity with bookId: {}", bookId);
        repository.findByBookId(bookId).ifPresent(e-> repository.delete(e));
    }


}
