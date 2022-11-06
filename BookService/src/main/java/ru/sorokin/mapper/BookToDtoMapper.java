package ru.sorokin.mapper;

import org.mapstruct.Mapper;
import ru.sorokin.model.Book;
import ru.sorokin.model.BookRequest;

@Mapper(componentModel = "spring")
public interface BookToDtoMapper {
    Book addBookRequestToBook(BookRequest bookRequest);
}
