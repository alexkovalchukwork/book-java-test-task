package com.example.demo.repository;


import com.example.demo.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
  boolean existsByIsbn(String isbn);
  List<Book> findByAuthorContainingIgnoreCase(String author);
}
