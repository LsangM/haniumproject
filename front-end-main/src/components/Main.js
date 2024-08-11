import React, { useState, useEffect } from "react";
import Sidebar from "./TranslatorComponents/Sidebar";
import Translator from "./Translator";
import Header from "./TranslatorComponents/Header";
import ChatBotModal from "./ChatBotModal";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { setToken, clearToken } from "../redux/actions";
import axios from "axios";

import mockWorkList from "../assets/mockdata/mockWorkList";
import mockWorkListAdd from "../assets/mockdata/mockWorkListAdd";

const Main = () => {
    const [chatOpen, setChatOpen] = useState(false); // 챗봇 표시 여부
    const [sidebarOpen, setSidebarOpen] = useState(false); // 사이드바 표시 여부
    const [selectedWork, setSelectedWork] = useState(0); // 선택한 작업
    const [workList, setWorkList] = useState([]); // 작업 목록
    const [workId, setWorkId] = useState(); // 작업 아이디
    const [results, setResults] = useState([]); // 결과물 저장 배열
    const [title, setTitle] = useState("작업 1"); // 번역 결과 제목
    const token = useSelector((state) => state.token.token); // 백엔드에서 반환받는 토큰
    const navigate = useNavigate();


    // 방문 시 로그인 여부 확인
    useEffect(() => {
        // checkLogIn();
        // fetchWorkList();
        setWorkList(mockWorkList);
    }, [])

    // 결과 목데이터 적용
    useEffect(() => {
        if(workList[selectedWork]) {
            const newResults = workList[selectedWork].results;
            setResults(newResults);
        }
    }, [selectedWork, workList])

    // 로그인 여부 확인 함수
    const checkLogIn = () => {
        if(token) {
            return;
        } else {
            navigate('/LogIn');
        }
    };

    const fetchWorkList = () => {
        // axios.get("http://localhost:8080/worklist", {
        //     headers: {
        //         'Authorization': `Bearer ${token}`
        //     }
        // }).then(res => {
        //     setWorkList(res.data.data);
        // }).catch(error => {
        //     console.log(error);
        // });
    };

    // 사이드바 토글 함수
    const toggleSidebar = () => {
        setSidebarOpen(!sidebarOpen);
    };

    return (
        <div className="container">
            <ChatBotModal open={chatOpen} setOpen={setChatOpen} />
            <Sidebar
                sidebarOpen={sidebarOpen}
                toggleSidebar={toggleSidebar}
                selectedWork={selectedWork}
                setSelectedWork={setSelectedWork}
                workList={workList}
                setWorkList={setWorkList}
                fetchWorkList={fetchWorkList}
                setResults={setResults}
                setWorkId={setWorkId}
                setTitle={setTitle}
                mockWorkListAdd={mockWorkListAdd}
            />
            <div className={`content ${sidebarOpen ? '' : 'expanded'}`}>
                <Header
                    sidebarOpen={sidebarOpen}
                    toggleSidebar={toggleSidebar}
                />
                <Translator 
                    setChatOpen={setChatOpen}
                    results={results}
                    setResults={setResults}
                    workId={workId}
                    title={title}
                    setTitle={setTitle}
                    setWorkList={setWorkList}
                />
            </div>
        </div>
    );
};

export default Main;