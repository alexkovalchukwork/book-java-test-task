package com.example.demo.controller;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
public class BookController {

  private final BookService svc;

  @GetMapping
  public List<BookResponse> getAll() {
    return svc.findAll();
  }

  @GetMapping("/{id}")
  public BookResponse getById(@PathVariable Long id) {
    return svc.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponse create(@Valid @RequestBody BookRequest req) {
    return svc.create(req);
  }

  @PutMapping("/{id}")
  public BookResponse update(@PathVariable Long id,
      @Valid @RequestBody BookRequest req) {
    return svc.update(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    svc.delete(id);
  }

  @GetMapping("/search")
  public List<BookResponse> searchByAuthor(@RequestParam String author) {
    return svc.findByAuthor(author);
  }
}
