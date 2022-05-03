package com.metacoding.book.domain;

//단위Test DB관련 Bean들만 memory에 뜨면 된다

import com.metacoding.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) //가짜DB로 Test replae.none실제DB
@DataJpaTest //Repository들을 다 Ioc에 등록
public class BookRepositoryUnitTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트(){
        //BODMockito방식
        // given
        Book book = new Book(null, "책제목", "저자");
        //when
        Book bookEntity = bookRepository.save(book);

        //Test실행

        //Then
        assertEquals("책제목", bookEntity.getTitle());
    }

}
