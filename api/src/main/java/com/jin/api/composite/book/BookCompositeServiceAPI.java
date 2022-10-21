package com.jin.api.composite.book;

import org.springframework.web.bind.annotation.*;

public interface BookCompositeServiceAPI {

    @GetMapping(
            value="/book-composite/{bookId}",
            produces = "application/json"
    )

    BookAggregate getCompositeBook(@PathVariable int bookId);

    @PostMapping(
            value = "book-composite",
            consumes = "application/json"
    )
    void createCompositeBook(@RequestBody BookAggregate model);

    @DeleteMapping(value = "/book-composite/{bookId}")
    void deleteCompositeBook(@PathVariable int bookId);

}
