package com.example.demo.service;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import java.util.List;

public interface BookService {

  List<BookResponse> findAll();

  BookResponse findById(Long id);

  BookResponse create(BookRequest req);

  BookResponse update(Long id, BookRequest req);

  void delete(Long id);

  List<BookResponse> findByAuthor(String author);
}
