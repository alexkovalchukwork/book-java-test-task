package com.example.demo.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Year;

@Entity
@Table(name = "books", uniqueConstraints = {
    @UniqueConstraint(columnNames = "isbn", name = "uc_book_isbn")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Size(min = 2)
  private String title;

  @NotBlank
  private String author;

  @NotBlank
  private String isbn;

  @NotNull @Min(1900)
  private Integer publishYear;

  @NotNull @Positive
  private Double price;

  @AssertTrue(message = "publishYear must not exceed current year")
  @Transient
  private boolean isPublishYearValid() {
    if (publishYear == null) return true;
    return publishYear <= Year.now().getValue();
  }
}
