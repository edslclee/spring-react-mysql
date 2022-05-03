package com.metacoding.book.service;
import com.metacoding.book.domain.Book;
import com.metacoding.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//final로 정의된 service의 constructor를 자동으로 생성(bookRepository
@RequiredArgsConstructor
//기능을 정의할 수 있고, 트랜잭션을 관리할 수 있음.
@Service
public class BookService {
    // 함수 => 송금() -> 레포지토리에 여러개의 함수를 실행 -> commit or rollback
    private final BookRepository bookRepository;

    @Transactional
    public Book 저장하기(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new IllegalStateException("ID를 확인하세요!!"));
    }
    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }
    @Transactional
    public Book 수정하기(Long id, Book book){
        //Dirty Checking적용
        Book bookEntity = bookRepository.findById(id) //영속화(spring memory상에 book object를 가지고 있다.
                .orElseThrow(()-> new IllegalStateException("ID를 확인해 주세요"));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    } //이 함수가 종료될 때(transaction이 종료될때, 영속화 되어있던 object의 값이 commit 또는 Rollback이 실행됨 => Flush

    public String 삭제하기(Long id){
        bookRepository.deleteById(id); //오류 시 exception을 수행
        return "OK";
    }
}
