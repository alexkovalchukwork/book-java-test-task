package com.example.demo.mapper;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.entity.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {


  public static BookResponse toDto(Book b) {
    return BookResponse.builder()
        .id(b.getId())
        .title(b.getTitle())
        .author(b.getAuthor())
        .isbn(b.getIsbn())
        .publishYear(b.getPublishYear())
        .price(b.getPrice())
        .build();
  }

  public static Book fromReq(BookRequest r) {
    return Book.builder()
        .title(r.getTitle())
        .author(r.getAuthor())
        .isbn(r.getIsbn())
        .publishYear(r.getPublishYear())
        .price(r.getPrice())
        .build();
  }
}
