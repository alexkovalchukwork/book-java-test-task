package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

  private Long id;
  private String title;
  private String author;
  private String isbn;
  private Integer publishYear;
  private Double price;
}
