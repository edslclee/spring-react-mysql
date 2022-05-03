package com.metacoding.book.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //서버 실행 시 Table이 mapping됨(ORM)
public class Book {
   @Id  //pk를 해당 변수로 하겠다
   @GeneratedValue(strategy = GenerationType.IDENTITY) //해당 DB번호 증가 전략을 따라 간다
   private Long id;

   private String title;
   private String author;

}
