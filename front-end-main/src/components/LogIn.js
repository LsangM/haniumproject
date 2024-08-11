import React, { useEffect, useState } from "react";
import { Button, Checkbox, FormControlLabel, TextField } from "@mui/material";
import './styles/LogIn.css';
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { setToken, clearToken } from "../redux/actions";

const LogIn = () => {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const navigate = useNavigate();
    const token = useSelector((state) => state.token.token);
    const dispatch = useDispatch();

    // 로그인 상태 확인
    useEffect(() => {
        checkLogIn();
    }, []);

    const checkLogIn = () => {
        if(token) {
            navigate('/');
        }
    };

    const handleSubmit = (e) => {
        // e.preventDefault();
        // axios.post("http://localhost:8080/auth/LogIn", {
        //     email: email, 
        //     password: password,
        // }).then((res) => {
        //     dispatch(setToken(res.data.token));
            navigate('/');
        // }).catch((error) => {
        //     console.log("Error while logging in : ", error);
        // });
    }

    return (
        <div className="logInWrapper">
            <div className="logInDiv">
                <h3>로그인</h3>
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
                    sx={{ mb: 3 }}
                /><br />
                <Button
                    className="LogInButton"
                    type="submit"
                    variant="contained"
                    onClick={handleSubmit}
                    sx={{ mb: 3 }}
                >로그인</Button><br />
                <div className="signUpLink">
                    아직 계정이 없으신가요?
                    <Button href="/SignUp">가입하기</Button>
                </div>
            </div>
        </div>

    );
};

export default LogIn;