package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.BookController;
import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  MockMvc mvc;
  @Autowired
  ObjectMapper mapper;

  @MockitoBean
  BookService svc;

  @Test
  void createAndGetBook() throws Exception {
    BookRequest req = BookRequest.builder()
        .title("My Book")
        .author("John Doe")
        .isbn("ISBN123")
        .publishYear(2020)
        .price(29.99)
        .build();

    given(svc.create(any())).willReturn(
        BookResponse.builder()
            .id(1L)
            .title(req.getTitle())
            .author(req.getAuthor())
            .isbn(req.getIsbn())
            .publishYear(req.getPublishYear())
            .price(req.getPrice())
            .build()
    );

    mvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(req)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("My Book"));
  }

  @Test
  void validationError() throws Exception {
    BookRequest bad = BookRequest.builder()
        .title("A")
        .author("")
        .isbn("")
        .publishYear(1899)
        .price(-5.0)
        .build();

    mvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bad)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.validationErrors").isArray());
  }
}
