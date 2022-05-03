import React from 'react';
import { Button, Form, FormControl, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <div>
      <Navbar bg="dark" variant="dark">
        <Link to = "/" className='navbar-brand'>Home</Link>
        <Nav className="me-auto">
          <Link to = "/joinForm" className='nav-link'>회원가입</Link>
          <Link to = "/loginForm" className='nav-link'>로그인</Link>
          <Link to = "/saveForm" className='nav-link'>글쓰기</Link>
        </Nav>
        <Form inline>
          <FormControl type="text" placeholder='Search' classname='mr-sm-2' />
        </Form> 
        <Button variant="outline-info">Search</Button>
      </Navbar>
      <br />
    </div>
  );
};

export default Header;