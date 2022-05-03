import React, { useEffect, useState } from 'react';
import Axios from 'axios';
import BookItem from '../../components/BookItem';

const Home = () => {

  const [books, setBooks] = useState([]);
  //함수 실행시 최초 한번 실행되는 
  const baseUrl = "http://localhost:8080";

  async function 모두가져오기() {
    await Axios
      .get(baseUrl + '/book/')
      .then((response) => {
        setBooks(response.data)
      })
      .catch((error) => {
        console.log(error)
      }); 
  }
  useEffect(() => {
    모두가져오기();
  },[]);

  return (

    <div>
      { books.map((book) => (
        <BookItem key={book.id} book={book} />
      ))}
    </div>
  );
};

export default Home;