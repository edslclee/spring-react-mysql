import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Form } from 'react-bootstrap';

const SaveForm = () => {

  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const navigate = useNavigate();

  const changeValue = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };


  const submitBook = (e) => {

    e.preventDefault(); //submit이 아무것도 action을 안타고 자기 할일만 함.

    fetch("http://localhost:8080/book/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        // Catch는 여기서 오류가 나야 실행됨.
        if (res !== null) {
          navigate('/');
        } else {
          alert('책 등록에 실패하였습니다.');
        }
      });
  }

  return (
    <div>
      <Form onSubmit={submitBook}>
        <Form.Group className="mb-3">
          <Form.Label>제목</Form.Label>
          <Form.Control type="text" placeholder="제목을 입력하세요" onChange={changeValue} name="title" />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>작성자</Form.Label>
          <Form.Control type="text" placeholder="작성자를 입력하세요" onChange={changeValue} name="author" />
        </Form.Group>
        <Button variant="primary" type="submit" class="btn btn-info">
          글쓰기
        </Button>
      </Form>
    </div>
  );
};

export default SaveForm;