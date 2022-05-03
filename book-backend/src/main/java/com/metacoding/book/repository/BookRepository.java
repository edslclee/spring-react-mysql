package com.metacoding.book.repository;

import com.metacoding.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
//@Repository적어야 스프링 IoC에 빈으로 등록되는데,
//JPA repository로 extends하면 생략가능
//JPA repository의 CRUD사용

public interface BookRepository extends JpaRepository <Book, Long>{
}
