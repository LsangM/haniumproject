import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import React, { useState, useEffect, useRef } from "react";

const InputSection = ({ message, loading, handleSubmit, setMessage, contextInput, setContextInput }) => {
  const inputRef = useRef();
  const contextInputRef = useRef();
  const [context01, setContext01] = useState("");
  const [context02, setContext02] = useState("");
  const [context03, setContext03] = useState("");

  // 입력창 크기 자동 조절
  useEffect(() => {
    const textarea = inputRef.current;
    textarea.style.height = '150px';
    textarea.style.height = `${textarea.scrollHeight}px`;
  }, [message]);

  // 입력창 크기 자동 조절
  // useEffect(() => {
  //   const textarea = contextInputRef.current;
  //   textarea.style.height = 'auto';
  //   textarea.style.height = `${textarea.scrollHeight}px`;
  // }, [contextInput]);

  const [workType, setWorkType] = React.useState('');

  const handleChange = (event) => {
    setWorkType(event.target.value);
  };

  return (
    <div className="inputSection">
      <h3>입력</h3>
      <select name="select" style={{marginBottom:'20px', padding:'5px', borderRadius:'5px'}}>
        <option value="none" selected>작업</option>
        <option value="option 1">번역</option>
        <option value="option 2">작문</option>
        <option value="option 3">교정</option>
      </select>

      <div className="inputDiv">
        <textarea
          className="input-main"
          ref={inputRef}
          type="text"
          placeholder="텍스트를 입력해주세요."
          value={message}
          onChange={(e) => {
            setMessage(e.target.value);
          }}
        ></textarea>
      </div>
      <div className="contextDiv">
        {/* <span 
          className="contextButton"
          onClick={toggleContextArea}
        >원문에 대한 추가 정보를 제공해 번역의 품질을 높일 수 있습니다.</span> */}
        <textarea
          className="input-context"
          type="text"
          placeholder="무엇에 관한 글인가요?"
          value={context01}
          onChange={(e) => {
            setContext01(e.target.value);
          }}
        ></textarea>
        <textarea
          className="input-context"
          type="text"
          placeholder="어떤 문체의 결과를 원하세요?"
          value={context02}
          onChange={(e) => {
            setContext02(e.target.value);
          }}
        ></textarea>
        <textarea
          className="input-context"
          type="text"
          placeholder="추가적인 정보가 있나요?"
          value={context03}
          onChange={(e) => {
            setContext03(e.target.value);
          }}
        ></textarea>
      </div>
      <div className="generate-button" onClick={handleSubmit} disabled={loading}>
        생성
      </div>
      <br />
      {loading && <span>로딩 중...</span>}
    </div>
  );
};

export default InputSection;
