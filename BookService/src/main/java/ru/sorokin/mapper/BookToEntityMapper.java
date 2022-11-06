package ru.sorokin.mapper;

import org.mapstruct.Mapper;
import ru.sorokin.entity.BookEntity;
import ru.sorokin.model.Book;

@Mapper(componentModel = "spring")
public interface BookToEntityMapper {
    BookEntity bookToBookEntity(Book book);

    Book bookEntityToBook(BookEntity bookEntity);
}
