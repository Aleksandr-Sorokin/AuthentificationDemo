package ru.sorokin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sorokin.entity.BookEntity;
import ru.sorokin.exceptions.NotFoundException;
import ru.sorokin.mapper.BookToEntityMapper;
import ru.sorokin.model.Book;
import ru.sorokin.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultBookService implements BookService {
    private final BookRepository bookRepository;
    private final BookToEntityMapper mapper;

    @Override
    public Book getBookById(Long id) {
        BookEntity bookEntity = bookRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found book with id %s", id)));
        return mapper.bookEntityToBook(bookEntity);
    }

    @Override
    public List<Book> getAllBooks() {
        Iterable<BookEntity> iterable = bookRepository.findAll();
        List<Book> books = new ArrayList<>();
        iterable.forEach(bookEntity -> books.add(mapper.bookEntityToBook(bookEntity)));
        return books;
    }

    @Override
    public void addBook(Book book) {
        BookEntity entity = mapper.bookToBookEntity(book);
        bookRepository.save(entity);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        Iterable<BookEntity> bookEntities = bookRepository.findAllByAuthorContaining(author);
        bookEntities.forEach(bookEntity -> books.add(mapper.bookEntityToBook(bookEntity)));
        return books;
    }
}
