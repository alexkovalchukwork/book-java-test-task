package com.example.demo.service.impl;

import com.example.demo.controller.exception.DuplicateIsbnException;
import com.example.demo.controller.exception.ResourceNotFoundException;
import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository repo;

  @Override
  public List<BookResponse> findAll() {
    return repo.findAll().stream().map(BookMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public BookResponse findById(Long id) {
    Book b = repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    return BookMapper.toDto(b);
  }

  @Override
  public BookResponse create(BookRequest req) {
    if (repo.existsByIsbn(req.getIsbn())) {
      throw new DuplicateIsbnException(req.getIsbn());
    }
    return BookMapper.toDto(repo.save(BookMapper.fromReq(req)));
  }

  @Override
  public BookResponse update(Long id, BookRequest req) {
    Book existing = repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    if (!existing.getIsbn().equals(req.getIsbn()) && repo.existsByIsbn(req.getIsbn())) {
      throw new DuplicateIsbnException(req.getIsbn());
    }
    existing.setTitle(req.getTitle());
    existing.setAuthor(req.getAuthor());
    existing.setIsbn(req.getIsbn());
    existing.setPublishYear(req.getPublishYear());
    existing.setPrice(req.getPrice());
    return BookMapper.toDto(repo.save(existing));
  }

  @Override
  public void delete(Long id) {
    Book b = repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    repo.delete(b);
  }

  @Override
  public List<BookResponse> findByAuthor(String author) {
    return repo.findByAuthorContainingIgnoreCase(author)
        .stream().map(BookMapper::toDto).collect(Collectors.toList());
  }
}
