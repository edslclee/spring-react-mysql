package com.metacoding.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.book.domain.Book;
import com.metacoding.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
*통합테스트(모든 Bean들을 똑같이 IoC올리고 테스트 하는것 *
* WebEnvironment.MOCK = 실제 톰캣이 아니라 다른 톰캣으로 테스트
* WebEnvironment.RANDOM_PORT = 실제 톰캣을 사용
* @AutoConfigureMockMvc MockMvc를 Ioc에 등록
* @Transactional은 각각의 테스트함수가 종료될때마다 트랜잭션을 rollback해주는 Annotation
*/
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //실제 톰캣이 아니라 다른 톰캣으로
public class BookControllerIntegrateTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init(){
        //entityManager.persist(new Book); JPA가 아니면
        //entityManager.createNativeQuery("ALTER TABLE book ALTER id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT=1").executeUpdate();
    }

    /* 실제 환경에서는 필요가 없다
    @MockBean //IoC환경에 등록됨 가짜
    private BookService bookService;
    */

    //BDDMockito Pattern given, when, then
    @Test
    public void save_테스트() throws Exception {
        //log.info("save_테스트()시작 ============================");
        // given(test를 위한 준비)
        Book book = new Book(null, "스프링 따라하기", "코스");
        String content = new ObjectMapper().writeValueAsString(book);

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
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "코스"));
        books.add(new Book(2L, "리액트 따라하기", "코스"));
        books.add(new Book(3L, "Junit5 따라하기", "코스"));
        bookRepository.saveAll(books);

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[2].title").value("Junit5 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findById_테스트() throws Exception {
        //given - 실제 db가 아님
        Long id = 2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "코스"));
        books.add(new Book(2L, "리액트 따라하기", "코스"));
        books.add(new Book(3L, "Junit5 따라하기", "코스"));
        bookRepository.saveAll(books);

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("리액트 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_테스트() throws Exception {
        //log.info("save_테스트()시작 ============================");
        // given(test를 위한 준비)
        Long id = 3L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링부트 따라하기", "코스"));
        books.add(new Book(null, "리엑트 따라하기", "코스"));
        books.add(new Book(null, "JUnit 따라하기", "코스"));
        bookRepository.saveAll(books);

        Book book = new Book(null, "C++ 따라하기", "코스");
        String content = new ObjectMapper().writeValueAsString(book);

        //when(Test실행)
        ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

}
