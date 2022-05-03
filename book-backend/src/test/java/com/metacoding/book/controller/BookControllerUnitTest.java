package com.metacoding.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.book.domain.Book;
import com.metacoding.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//단위테스트 - controller만 test Filter, ControllAdvice등
//@ExtendWith(SpringExtension.class) : Junit4에서는 spring으로 test위해 반드시 지정

@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //IoC환경에 등록됨 가짜
    private BookService bookService;

    //BDDMockito Pattern given, when, then
    @Test
    public void save_테스트() throws Exception {
        //log.info("save_테스트()시작 ============================");
        // given(test를 위한 준비)
        Book book = new Book(null, "스프링 따라하기", "코스");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.저장하기(book)).thenReturn(new Book(1L, "스프링 따라하기", "코스"));

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(post("/book")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAll_테스트() throws Exception {
        //given - 실제 db가 아님
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "코스"));
        books.add(new Book(2L, "리액트 따라하기", "코스"));
        when(bookService.모두가져오기()).thenReturn(books);

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findById_테스트() throws Exception {
        //given - 실제 db가 아님
        Long id = 1L;
        when(bookService.한건가져오기(id)).thenReturn((new Book(1L, "자바 공부하기", "ssar")));

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("자바 공부하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_테스트() throws Exception {
        //log.info("save_테스트()시작 ============================");
        // given(test를 위한 준비)
        Long id = 1L;
        Book book = new Book(null, "C++ 따라하기", "코스");
        String content = new ObjectMapper().writeValueAsString(book);

        when(bookService.수정하기(id, book)).thenReturn((new Book(1L, "C++ 따라하기", "코스")));

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_테스트() throws Exception {
        //log.info("save_테스트()시작 ============================");
        // given(test를 위한 준비)
        Long id = 1L;
        when(bookService.삭제하기(id)).thenReturn("OK");

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(delete("/book/{id}", id)
                .accept(MediaType.TEXT_PLAIN));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultActions.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("OK", result);
    }
}
