import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { Button, Form } from 'react-bootstrap';

const UpdateForm = () => {

  const { id } = useParams();
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch('http://localhost:8080/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  },[]);

  const navigate = useNavigate();

  const changeValue = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };


  const submitBook = (e) => {

    e.preventDefault(); //submit이 아무것도 action을 안타고 자기 할일만 함.

    fetch("http://localhost:8080/book/" + id, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        if (res.status === 200) {
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
          alert('책 수정에 실패하였습니다.');
        }
      });
  }

  return (
    <div>
      <Form onSubmit={submitBook}>
        <Form.Group className="mb-3">
          <Form.Label>제목</Form.Label>
          <Form.Control type="text" placeholder="제목을 입력하세요" onChange={changeValue} name="title" value={book.title} />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>작성자</Form.Label>
          <Form.Control type="text" placeholder="작성자를 입력하세요" onChange={changeValue} name="author" value={book.author}/>
        </Form.Group>
        <Button variant="primary" type="submit" class="btn btn-info">
          글수정하기
        </Button>
      </Form>
    </div>
  );
};

export default UpdateForm;