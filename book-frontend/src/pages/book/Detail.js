import React, { useEffect, useState } from 'react';
import Axios from 'axios';
import { useNavigate } from 'react-router';
import { useParams } from 'react-router-dom';
import { Button } from 'react-bootstrap';

const Detail = (props) => {

  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState({
    id: '',
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

  const deleteBook = () => {
    console.log(2, id);
    fetch('http://localhost:8080/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res !== null) {
          navigate('/');
        } else {
          alert('삭제실패');
        }
      });
  };

  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  
  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning" onClick={updateBook}>
        수정
      </Button>
      {'  '}
      <Button variant="danger" onClick={deleteBook}>
        삭제
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;