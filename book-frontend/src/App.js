//글쓰기, 글삭제, 글 목록 보기
import { Route, Routes } from "react-router-dom";
import {Container} from 'react-bootstrap'
import Header from "./components/Header";
import Home from "./pages/book/Home";
import SaveForm from "./pages/book/SaveForm";
import Detail from "./pages/book/Detail";
import LoginForm from "./pages/user/LoginForm";
import JoinForm from "./pages/user/JoinForm";
import UpdateForm from "./pages/book/UpdateForm";


function App() {
  return (
    <div>
    <Header />
    <Container>
      <Routes> 
          <Route path="/" element={<Home />}></Route>
          <Route path="/saveForm" element={<SaveForm />}></Route>
          <Route path="/book/:id" element={<Detail />}></Route>
          <Route path="/loginForm" element={<LoginForm />}></Route> 
          <Route path="/joinForm" element={<JoinForm />}></Route>
          <Route path="/updateForm/:id" element={<UpdateForm />}></Route>     
        </Routes>
    </Container>
    </div>
  );
}

export default App;
