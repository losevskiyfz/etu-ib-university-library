package com.losevskiyfz.reactivestackresearchwork.controller;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import com.losevskiyfz.reactivestackresearchwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/book")
public class BookRecordController {

    private final BookService bookService;

    @Autowired
    public BookRecordController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    ResponseEntity<BookRecord> save(@RequestBody BookRecord bookRecord) {
        BookRecord savedBookRecord = bookService.save(bookRecord);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedBookRecord.id())
                                .toUri()
                )
                .body(savedBookRecord);
    }

    @GetMapping
    public Page<BookRecord> get(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return bookService.getPaginated(page, size);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        bookService.delete(id);
    }
}
