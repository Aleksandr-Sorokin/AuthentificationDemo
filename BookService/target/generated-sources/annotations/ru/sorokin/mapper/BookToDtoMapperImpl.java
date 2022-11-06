package ru.sorokin.mapper;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import ru.sorokin.model.Book;
import ru.sorokin.model.BookRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-06T22:06:52+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class BookToDtoMapperImpl implements BookToDtoMapper {

    @Override
    public Book addBookRequestToBook(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        String author = null;
        String title = null;
        Double price = null;

        author = bookRequest.getAuthor();
        title = bookRequest.getTitle();
        price = bookRequest.getPrice();

        Long id = null;

        Book book = new Book( id, author, title, price );

        return book;
    }
}
