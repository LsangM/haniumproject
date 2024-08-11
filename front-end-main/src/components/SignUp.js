import React, {useState} from "react";
import { Button, Checkbox, FormControlLabel, TextField } from "@mui/material";
import './styles/SignUp.css';
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { setToken, clearToken } from "../redux/actions";

const SignUp = () => {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordCheck, setPasswordCheck] = useState();
    const [passwordNotSame, setPasswordNotSame] = useState(false);
    const navigate = useNavigate();
    const token = useSelector((state) => state.token.token);
    const [userName, setUserName] = useState('');
    const [userField, setUserField] = useState('');
    const [userInterest, setUserInterest] = useState('');
    const [userLanguage, setUserLanguage] = useState('');

    const handleSubmit = (e) => {
        // e.preventDefault();
        // // 비밀번호 같게 적었는지 검사
        // if(password !== passwordCheck) {
        //     setPasswordNotSame(true);
        //     return;
        // }

        // axios.post("http://localhost:8080/auth/SignUp", {
        //     email: email, 
        //     password: password,
        // }).then((res) => {
        //     navigate('/LogIn');
        // }).catch((error) => {
        //     console.log("Error while Sign Up : ", error);
        // });
        navigate('/SignUpDetail');
    }

    return (
        <div className="SignUpWrapper">
            <div className="SignUpDiv">
                <h3>회원가입</h3>
                <TextField
                    className="TextField"
                    label="이메일"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    content={email}
                    onChange={(e) => {setEmail(e.target.value)}}
                    sx={{ mb: 2 }}
                /><br />
                <TextField
                    className="TextField"
                    label="비밀번호"
                    type="password"
                    name="password"
                    content={password}
                    onChange={(e) => {setPassword(e.target.value)}}
                    sx={{ mb: 2 }}
                /><br />
                <TextField
                    className="TextField"
                    label="비밀번호 확인"
                    type="password"
                    name="password"
                    content={passwordCheck}
                    onChange={(e) => {setPasswordCheck(e.target.value)}}
                    sx={{ mb: 3 }}
                /><br />
                <Button
                    className="LogInButton"
                    type="submit"
                    variant="contained"
                    onClick={handleSubmit}
                    sx={{ mb: 3 }}
                >계속</Button><br />
                <div className="signUpLink">
                    이미 계정이 있으신가요?
                    <Button href="/LogIn">로그인</Button>
                </div>
            </div>
        </div>

    );
};

export default SignUp;