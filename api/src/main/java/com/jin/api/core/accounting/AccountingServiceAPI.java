package com.jin.api.core.accounting;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AccountingServiceAPI {

    @GetMapping(
            value = "/accounting",
            produces = "application/json"
    )

    List<Accounting> getAccounting(@RequestParam(value = "bookId", required=true) int bookId);

    @PostMapping(
            value = "/accounting",
            consumes = "application/json",
            produces = "application/json")
    Accounting createAccounting(@RequestBody Accounting model);

    @DeleteMapping(value = "/accounting")
    void deleteAccountings(@RequestParam(value = "bookId", required = true) int bookId);
}
