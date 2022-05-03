package com.metacoding.book.service;

import com.metacoding.book.domain.Book;
import com.metacoding.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * 단위 테스트(service에 관련된 애들만 메모리에 띄우면 됨
 * 여기서는 BookRepository => 임의로 만드 수 있음
 */

@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
    @InjectMocks //BookService 객체가 만들어질때 BookServiceUnitTest 화일에 @Mock로 등록된 모든 애들을 주입받는다
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트(){
        //BODMockito방식
        // given
        Book book = new Book();
        book.setTitle("책제목");
        book.setAuthor("저자");

        //stub 동작 지정
        when(bookRepository.save(book)).thenReturn(book);
        //Test실행
        Book bookEntity = bookService.저장하기(book);
        //Then
        assertEquals(bookEntity, book);

     }

}
