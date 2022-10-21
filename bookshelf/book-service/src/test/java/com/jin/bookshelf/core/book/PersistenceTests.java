package com.jin.bookshelf.core.book;

import com.jin.bookshelf.core.book.datalayer.BookEntity;
import com.jin.bookshelf.core.book.datalayer.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class PersistenceTests {

    @Autowired
    private BookRepository repository;
    private BookEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        BookEntity entity = new BookEntity(1, "t", "au", "art", "s", "p", "t", "a", "st");
        savedEntity = repository.save(entity);

        //expected, actual
        //assertEqualsProduct(entity, savedEntity);

        //actual, expected
        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void getByBookId(){
        Optional<BookEntity> entity = repository.findByBookId(savedEntity.getBookId());
        assertTrue(entity.isPresent());
        //assertEqualsProduct(savedEntity, entity.get();
        assertThat(entity.get(), samePropertyValuesAs(savedEntity));
    }

    @Test
    public void createBookEntity() {

        BookEntity newEntity = new BookEntity(2, "t", "au", "art", "s", "p", "t", "a", "st");
        repository.save(newEntity);

        BookEntity foundEntity = repository.findById(newEntity.getId()).get();
        //assertEqualsProduct(newEntity, foundEntity);

        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void updateProductEntity() {
        savedEntity.setTitle("t2");
        repository.save(savedEntity);

        BookEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("t2", foundEntity.getTitle());
    }

    @Test
    public void deleteBookEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }


    @Test
    public void getBookEntity() {
        Optional<BookEntity> entity = repository.findByBookId(savedEntity.getBookId());

        assertTrue(entity.isPresent());
        //assertEqualsProduct(savedEntity, entity.get());

        assertThat(entity.get(), samePropertyValuesAs(savedEntity));

    }

    private void assertEqualsBook(BookEntity expectedEntity, BookEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getBookId(),        actualEntity.getBookId());
        assertEquals(expectedEntity.getTitle(),           actualEntity.getTitle());
        assertEquals(expectedEntity.getAuthor(),           actualEntity.getAuthor());
        assertEquals(expectedEntity.getArtist(),           actualEntity.getArtist());
        assertEquals(expectedEntity.getSubject(),           actualEntity.getSubject());
        assertEquals(expectedEntity.getPublisher(),           actualEntity.getPublisher());
        assertEquals(expectedEntity.getType(),           actualEntity.getType());
        assertEquals(expectedEntity.getApp(),           actualEntity.getApp());
        assertEquals(expectedEntity.getStatus(),           actualEntity.getStatus());
    }

}
