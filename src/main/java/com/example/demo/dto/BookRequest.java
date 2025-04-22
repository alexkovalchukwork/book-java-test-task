package com.example.demo.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

  @NotBlank
  @Size(min = 2)
  private String title;

  @NotBlank
  private String author;

  @NotBlank
  private String isbn;

  @NotNull
  @Min(1900)
  private Integer publishYear;

  @NotNull
  @Positive
  private Double price;

  @AssertTrue(message = "publishYear must not exceed current year")
  public boolean isPublishYearValid() {
    if (publishYear == null) {
      return true;
    }
    return publishYear <= Year.now().getValue();
  }
}
