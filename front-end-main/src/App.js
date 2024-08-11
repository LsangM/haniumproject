import './App.css';
import './input.css';
import { BrowserRouter, Routes, Route, } from "react-router-dom";
import LogIn from './components/LogIn';
import SignUp from './components/SignUp';
import Main from './components/Main';
import Onboarding from './components/Onboarding';
import MyPage from './components/MyPage';
import SignUpDetail from './components/SignUpDetail';


function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Main />} />
          <Route path='/LogIn' element={<LogIn />} />
          <Route path='/SignUp' element={<SignUp />} />
          <Route path='/SignUpDetail' element={<SignUpDetail />} />
          <Route path='/Onboarding' element={<Onboarding />} />
          <Route path='/MyPage' element={<MyPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
