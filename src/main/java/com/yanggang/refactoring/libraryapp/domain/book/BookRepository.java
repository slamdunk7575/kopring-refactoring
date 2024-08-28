package com.yanggang.refactoring.libraryapp.domain.book;

import com.yanggang.refactoring.libraryapp.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String bookName);

}
