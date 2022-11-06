package ru.sorokin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sorokin.mapper.BookToDtoMapper;
import ru.sorokin.model.Book;
import ru.sorokin.model.BookRequest;
import ru.sorokin.service.BookService;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookToDtoMapper dtoMapper;

    @GetMapping(path = "/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String author) {
        if (author != null) {
            return bookService.findByAuthor(author);
        }
        return bookService.getAllBooks();
    }

    @PostMapping
    public void addBook(@RequestBody BookRequest bookRequest) {
        bookService.addBook(dtoMapper.addBookRequestToBook(bookRequest));
    }
}
