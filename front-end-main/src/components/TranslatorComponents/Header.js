import React, { useState } from "react";
import '../styles/Header.css';
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { clearToken } from "../../redux/actions";
import { useNavigate } from "react-router-dom";

const Header = ({ sidebarOpen, toggleSidebar }) => {
    const serviceName = "IN OTHER WORDS";
    const token = useSelector((state) => state.token.token);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleTitleClick = () => {
        window.location.reload();
    };

    // const handleLogOutClick = () => {
    //     dispatch(clearToken());
    // };

    const handleMyPageClick = () => {
        navigate('/MyPage');
    };

    const handleLogOutClick = () => {
        navigate('/Onboarding');
    };

    return (
        <div className="headerDiv" style={{position:'relative'}}>
            <div className="sidebarButton"
                onClick={toggleSidebar}
                style={{ opacity: sidebarOpen ? "0" : "" }}>
                ☰
            </div>
            <div className="titleDiv" style={{position:'absolute', left:'50%', transform:'translateX(-50%)'}}
                onClick={handleTitleClick}>
                <span className="serviceName">{serviceName}</span>
            </div>
            <div className="userDiv" style={{display:'flex', gap:'10px'}}>
                <div className="myPage-button" onClick={handleMyPageClick}>마이페이지</div>
                <div className="myPage-button" onClick={handleLogOutClick}>로그아웃</div>
            </div>
        </div>
    );
};

export default Header;