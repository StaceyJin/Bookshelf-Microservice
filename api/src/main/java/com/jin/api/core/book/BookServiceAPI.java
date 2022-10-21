package com.jin.api.core.book;

import org.springframework.web.bind.annotation.*;

public interface BookServiceAPI {

    @GetMapping(
        value = "/book/{bookId}",
        produces = "application/json"
    )

    Book getBook(@PathVariable int bookId);

    @PostMapping(
            value = "/book",
            consumes = "application/json",
            produces = "application/json"
    )
    Book createBook(@RequestBody Book model);

    @DeleteMapping(value = "/book/{bookId}")
    void deleteBook(@PathVariable int bookId);
}
