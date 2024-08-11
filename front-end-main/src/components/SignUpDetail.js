import React, { useState } from "react";
import './styles/MyPage.css';
import { TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";

const SignUpDetail = () => {
    const [userName, setUserName] = useState('');
    const [userField, setUserField] = useState('');
    const [userInterest, setUserInterest] = useState('');
    const [userLanguage, setUserLanguage] = useState('');
    const navigate = useNavigate();

    const handleSignUpClick = () => {
        navigate('/LogIn');
    }

    return (
        <div className="MyPage-wrapper" style={{ display: 'grid', placeItems:'center', justifyContent: 'center', height:'100vh', background:'rgb(247, 247, 247)' }}>
            <div style={{background:'white', paddingTop:'80px', paddingBottom:'80px', borderRadius:'30px'}}>
                <div style={{ fontWeight: 'normal', fontSize: '3rem'}}>회원가입</div>
                <div className="MyPage" style={{ minWidth: '500px', display: 'flex', flexDirection: 'column', justifyContent:'center'}}>
                    <div style={{ alignContents: 'center', padding: '10px', marginTop: '80px',}}>
                        <span style={{ position:'absolute', marginRight: '20px', transform:'translateY(-30px)' }}>이름</span>
                        <input style={{ background:'none', margin:'0 auto', width: '300px', padding: '10px', fontFamily: 'Noto Sans KR', fontSize: "1rem", border: '1px solid gray', borderRadius: '6px' }}
                            value={userName} onChange={(e) => { setUserName(e.target.value) }} />
                    </div>
                    <div style={{ alignContents: 'center', padding: '10px', marginTop: '30px'}}>
                        <span style={{ position:'absolute', marginRight: '20px', transform:'translateY(-30px)' }}>직업 및 전문 분야</span>
                        <input style={{ background:'none', margin:'0 auto', width: '300px', padding: '10px', fontFamily: 'Noto Sans KR', fontSize: "1rem", border: '1px solid gray', borderRadius: '6px' }}
                            value={userField} onChange={(e) => { setUserField(e.target.value) }} />
                    </div>
                    <div style={{ alignContents: 'center', padding: '10px', marginTop: '30px'}}>
                        <span style={{ position:'absolute', marginRight: '20px', transform:'translateY(-30px)' }}>관심 분야</span>
                        <input style={{ background:'none', margin:'0 auto', width: '300px', padding: '10px', fontFamily: 'Noto Sans KR', fontSize: "1rem", border: '1px solid gray', borderRadius: '6px' }}
                            value={userInterest} onChange={(e) => { setUserInterest(e.target.value) }} />
                    </div>
                    <div style={{ alignContents: 'center', padding: '10px', marginTop: '30px'}}>
                        <span style={{ position:'absolute', marginRight: '20px', transform:'translateY(-30px)' }}>관심 언어</span>
                        <input style={{ background:'none', margin:'0 auto', width: '300px', padding: '10px', fontFamily: 'Noto Sans KR', fontSize: "1rem", border: '1px solid gray', borderRadius: '6px' }}
                            value={userLanguage} onChange={(e) => { setUserLanguage(e.target.value) }} />
                    </div>
                </div>
                <div style={{display:'flex', justifyContent:'center', marginTop:'50px', gap:'50px', fontSize:'1.2rem'}}>
                    <div className="button" onClick={handleSignUpClick}>가입하기</div>
                </div>
            </div>
        </div>
    )
};

export default SignUpDetail;