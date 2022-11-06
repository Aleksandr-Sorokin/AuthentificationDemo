package ru.sorokin.mapper;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import ru.sorokin.entity.BookEntity;
import ru.sorokin.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-06T22:06:52+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class BookToEntityMapperImpl implements BookToEntityMapper {

    @Override
    public BookEntity bookToBookEntity(Book book) {
        if ( book == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( book.getId() );
        bookEntity.setAuthor( book.getAuthor() );
        bookEntity.setTitle( book.getTitle() );
        bookEntity.setPrice( book.getPrice() );

        return bookEntity;
    }

    @Override
    public Book bookEntityToBook(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }

        Long id = null;
        String author = null;
        String title = null;
        Double price = null;

        id = bookEntity.getId();
        author = bookEntity.getAuthor();
        title = bookEntity.getTitle();
        price = bookEntity.getPrice();

        Book book = new Book( id, author, title, price );

        return book;
    }
}
