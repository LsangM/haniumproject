import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Slide from '@mui/material/Slide';
import { Divider, IconButton, CircularProgress } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import './styles/ChatBotModal.css';
import axios from 'axios';

import mockFeedback01 from '../assets/mockdata/mockFeedback';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

const ChatBotModal = ({open, setOpen}) => {
    const [message, setMessage] = useState("");
    const [messageList, setMessageList] = useState([]);
    const [loading, setLoading] = useState(false); // 전송 중 로딩 상태 관리

    const handleClose = () => {
        setMessage("");
        setMessageList([]);
        setOpen(false);
    };

    useEffect(() => {
        // messageList가 갱신될 때마다 스크롤을 아래로 이동시킴
        const scrollContainer = document.querySelector('.chatBox');
        if (scrollContainer) {
            scrollContainer.scrollTop = scrollContainer.scrollHeight;
        }
    }, [messageList]);

    // 전송 버튼 이벤트
    const handleSend = async () => {
        if (message.trim() !== "") {
            setLoading(true); // API 요청 전 로딩 상태 활성화

            //메시지가 비어있지 않을 경우에만 추가
            const newMessage = { text: message, user: true }

            setMessageList(prevMessageList => [...prevMessageList, newMessage]);
            setMessage("");

            //API 호출하여 챗봇의 답장 받기
            try {
                const response = await axios.post('http://localhost:8080/testChat', { content: message });
                const botMessage = { text: response.data, user: false };
                setMessageList(prevMessageList => [...prevMessageList, botMessage]);
            } catch (error) {
                console.log('Error while fetching chatbot response: ', error);
                const errorMessage = { text: "API 에러!", user: false };
                setMessageList(prevMessageList => [...prevMessageList, errorMessage]);
            }

            setLoading(false); // API 요청 후 로딩 상태 비활성화
        }
    };

    const mockHandleSend = () => {
        if (message.trim() !== "") {
            setLoading(true); // API 요청 전 로딩 상태 활성화

            //메시지가 비어있지 않을 경우에만 추가
            const newMessage = { text: message, user: true }

            setMessageList(prevMessageList => [...prevMessageList, newMessage]);
            setMessage("");

            setTimeout(() => {
                const response = { 
                    text: "물론입니다. 아래는 다시 작성한 내용입니다.\n\nDeep learning is a type of computer technology that's really smart. It works kind of like how our brains work. It's way better than the older computer learning methods. A smart person named Professor Geoffrey Hinton from the University of Toronto started it in 2006. Other really smart people like Yann LeCun and Andrew Ng also helped make it better. Now, these experts are working at big tech companies like Google, Facebook, and Baidu. They're making new things faster.",
                    user: false,
                }
                setMessageList(prevMessageList => [...prevMessageList, response]);
            }, 1000)

            setLoading(false); // API 요청 후 로딩 상태 비활성화
        }
    }

    // 엔터키로 전송 이벤트 실행
    const handleOnKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSend();
        }
    };

    return (
        <div className='ChatBotModal'>
            <Dialog
                open={open}
                TransitionComponent={Transition}
                keepMounted
                onClose={handleClose}
                aria-describedby="alert-dialog-slide-description"
                sx={{ color: 'beige' }}
            >
                <DialogTitle></DialogTitle>
                <DialogContent>
                    <div className="chatBox">
                        <div className="line">
                            <span className="chatBot">해당 문단에 대해 챗봇과 대화할 수 있습니다. 무엇을 도와드릴까요?</span>
                        </div>
                        {messageList.map((msg, index) => (
                            <div className="line" key={index}>
                                <div className={msg.user ? "user" : "chatBot"}>{msg.text}</div>
                            </div>
                        ))}
                    </div>
                </DialogContent>
                <Divider />
                <DialogActions sx={{ margin: "0 0 0 10px", display: "flex", justifyContent: "space-between" }}>
                    <TextField
                        autoFocus
                        id="name"
                        variant="standard"
                        margin='normal'
                        value={message}
                        onChange={(e) => { setMessage(e.target.value) }}
                        onKeyDown={loading ? null : handleOnKeyPress}
                        sx={{width: '450px'}}
                    />
                    {loading ? (
                        <CircularProgress size={20} color='inherit' sx={{marginRight: "10px"}}/>
                    ) : (
                        <IconButton onClick={mockHandleSend}>
                            <SendIcon />
                        </IconButton>
                    )}
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default ChatBotModal;
