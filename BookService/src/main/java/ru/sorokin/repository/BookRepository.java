package ru.sorokin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sorokin.entity.BookEntity;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    List<BookEntity> findAllByAuthorContaining(String author);
}
