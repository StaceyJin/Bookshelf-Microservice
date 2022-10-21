package com.jin.api.core.portal;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PortalServiceAPI {

    @GetMapping(
            value = "/portal",
            produces = "application/json"
    )

    List<Portal> getPortal(@RequestParam(value="bookId", required = true) int bookId);

    @PostMapping(
            value = "/portal",
            consumes = "application/json",
            produces = "application/json")
    Portal createPortal(@RequestBody Portal model);

    @DeleteMapping(value = "/portal")
    void deletePortals(@RequestParam(value = "bookId", required = true) int bookId);

}
