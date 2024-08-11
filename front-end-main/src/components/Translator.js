import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import './styles/Translator.css';
import OutputSection from "./TranslatorComponents/OutputSection";
import InputSection from "./TranslatorComponents/InputSection";
import ResultSection from "./TranslatorComponents/ResultSection";
import { useSelector, useDispatch } from "react-redux";

import mockOutputs from "../assets/mockdata/mockOutPuts";

const Translator = ({ setChatOpen, results, setResults, workId, title, setTitle, setWorkList }) => {
  const [loading, setLoading] = useState(false); // 전송 중 로딩 상태 관리
  const [isOutputNew, setIsOutputNew] = useState(true); // 매 요청마다 하나의 결과물만 저장하도록
  const [message, setMessage] = useState(""); // 번역 요청할 입력 원문
  const [outputs, setOutputs] = useState([]); // 입력 원문에 대한 결과물
  const [editingText, setEditingText] = useState(""); // 결과 문단 수정용
  const [originalText, setOriginalText] = useState(""); // 원문 저장용
  const [contextInput, setContextInput] = useState(""); // 문맥 정보 저장
  const [titleClicked, setTitleClicked] = useState(false); // 번역 결과 클릭 여부
  const [selectedText, setSelectedText] = useState(""); // 빠른 역번역 제공을 위한 선택 텍스트
  const [fastTranslation, setFastTranslation] = useState(""); // 파파고 api 번역 결과
  const [mouseX, setMouseX] = useState(0); // 파파고 번역 표시 위한 마우스 좌표 추적
  const [mouseY, setMouseY] = useState(0);
  const [showFastTranslation, setShowFastTranslation] = useState(false); // 파파고 번역 화면 표시 여부
  const token = useSelector((state) => state.token.token); // 백엔드에서 반환받는 토큰
  const [newTitle, setNewTitle] = useState(""); // 제목 변경 저장

  // 입력 전송 함수
  const handleSubmit = async () => {
    if (message.trim() !== "") {
      setLoading(true); // API 요청 전 로딩 상태 활성화

      //API 호출하여 챗봇의 답장 받기
      // try {
      //   const response = await axios.post(
      //     "http://localhost:8080/TranslationBuilderTest",
      //     // id는 임의로 설정한 것입니다. 나중에 빼기
      //     { content: message }
      //   );

      //   // 응답 데이터 처리
      //   if (Array.isArray(response.data)) {
      //     setOutputs(response.data);
      //   } else {
      //     console.log("Invalid API response format.");
      //   }
      // } catch (error) {
      //   console.log("Error while fetching chatbot response: ", error);
      // }
      setTimeout(() => {
        setOutputs(mockOutputs);
      }, 1000);
      setOriginalText(message);
      setIsOutputNew(true);
      setLoading(false);
    }
  };

  // 번역 결과 선택 저장 요청
  const handleSelection = (output) => {
    if (isOutputNew) {
      addResult(output);
      setIsOutputNew(false);
    } else {
      handleDeletion(results[results.length - 1]);
      setIsOutputNew(false);
      addResult(output);
    }
  };

  const addResult = (output) => {
    // axios.post('http://localhost:8080/results/' + workId, {
    //   content: output.content,
    //   original: originalText,
    // }, {
    //   headers: { 'Authorization': `Bearer ${token}` }
    // }).then(res => {
    //   setResults(res.data.data);
    // }).catch(error => {
    //   console.log(error);
    // })
    setResults([
      {
        content: "The deep learning algorithms, structured as neural networks mimicking the human brain, have transcended the limitations of traditional machine learning. Initially introduced by Professor Geoffrey Hinton of the University of Toronto in 2006, deep learning gained prominence through the contributions of global deep learning luminaries such as Yann LeCun and Andrew Ng. These experts have further advanced the field, and they are currently driving their research at global IT giants like Google, Facebook, and Baidu, accelerating the pace of innovation.",
        original: "인간의 뇌를 모방한 신경망 네트워크(neural networks) 구조로 이루어진 딥러닝 알고리즘은 기존 머신러닝의 한계를 더욱 뛰어넘게 했습니다. 2006년 캐나다 토론토대학의 제프리 힌튼 교수가 처음 발표하면서 알려지게 된 딥러닝은 얀 레쿤과 앤드류 응과 같은 세계적인 딥러닝 구루들에 의해 더욱 발전했고 현재 이들은 구글, 페이스북, 바이두 같은 글로벌 IT 회사에 영입되어 그 연구를 더욱 가속화가고 있습니다.",
      },
    ])
  };

  // 결과 문단 삭제 요청
  const handleDeletion = (result) => {

    axios.put('http://localhost:8080/results/delete/' + workId, {
      resultId: result.resultId,
    }, {
      headers: { 'Authorization': `Bearer ${token}` }
    }).then(res => {
      setResults(res.data.data);
    }).catch(error => {
      console.log(error);
    })

    setIsOutputNew(true);
  };

  // 드래그앤드랍 시 결과 순서 변경 요청
  const onDragEnd = async (draggable) => {
    if (!draggable.destination) return;

    axios.put('http://localhost:8080/results/reorder/' + workId, {
      resultId: results[draggable.draggableId].resultId,
      index: draggable.destination.index,
    }, {
      headers: { 'Authorization': `Bearer ${token}` }
    }).then(res => {
      setResults(res.data.data);
    }).catch(error => {
      console.log(error);
    })
  };

  // 결과 문단 클릭 시 수정창을 띄운다 (한 번에 하나만 가능)
  const handleResultClick = (content, targetIndex) => {
    setEditingText(content);

    setResults((prevResults) => {
      const updatedResults = prevResults.map((result) => ({ ...result, editing: false }));
      return updatedResults;
    });

    setResults((prevResults) => {
      const updatedResults = [...prevResults];
      updatedResults[targetIndex] = { ...updatedResults[targetIndex], editing: true };
      return updatedResults;
    });
  };

  // 문단 수정 요청 (비어있을 시 취소)
  const handleSaveClick = (resultId) => {
    if (editingText.trim() !== "") {
      axios.put('http://localhost:8080/results/' + workId, {
        resultId: resultId,
        content: editingText,
      }, {
        headers: { 'Authorization': `Bearer ${token}` }
      }).then(res => {
        setResults(res.data.data);
      }).catch(error => {
        console.log(error);
      })
    }
  };

  // 결과 제목 수정 관련 함수
  const handleTitleClick = () => {
    setTitleClicked(!titleClicked);
    setNewTitle(title);
  };

  const handleTitleBlur = () => {
    if (title.trim() !== "") {
      axios.put("http://localhost:8080/worklist", {
            workId: workId,
            title: newTitle,
        }, { 
            headers: { 'Authorization': `Bearer ${token}` }
        }).then(res => {
            setWorkList(res.data.data);
            setTitle(newTitle);
        }).catch(error => {
            console.log(error);
        });
    }
    setTitleClicked(false);
  };

  // 역번역 기능 함수
  const handleMouseUp = (e) => {
    const selection = window.getSelection();
    setSelectedText(selection.toString());
    setMouseY(e.clientY);
  }

  useEffect(() => {
    if (selectedText.trim() !== "") {
      fetchFastTranslation();
    }
  }, [selectedText]);

  useEffect(() => {
    if (fastTranslation) {
      setShowFastTranslation(true);
    }
  }, [fastTranslation])

  const fetchFastTranslation = async () => {
    try {
      const response = await axios.post('http://localhost:8080/ShortTranslationTest', { content: selectedText });
      const data = response.data;
      setFastTranslation(data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleBlur = () => {
    setShowFastTranslation(false);
  };

  const handleMouseDown = (e) => {
    setShowFastTranslation(false);
    setMouseX(e.clientX);
  };

  return (
    <div className="contentContainer"
      onMouseUp={(e) => { handleMouseUp(e); }}
      onBlur={handleBlur}
      onMouseDown={handleMouseDown}>
      {showFastTranslation && (
        <div className="fastTranlation"
          style={{ left: mouseX - 30, top: mouseY - 80 }}>{fastTranslation}</div>
      )}
      <div className="left">
        <div className="leftDiv">
          <InputSection
            message={message}
            loading={loading}
            handleSubmit={handleSubmit}
            setMessage={setMessage}
            contextInput={contextInput}
            setContextInput={setContextInput}
          />
          <OutputSection
            outputs={outputs}
            handleSelection={handleSelection}
          />
        </div>
      </div>
      <div className="right">
        <div className="resultsDiv">
          <div className="workTitleDiv">
            {titleClicked ? (
              <input
                className="workTitleInput"
                type="text"
                value={newTitle}
                onChange={(e) => {setNewTitle(e.target.value)}}
                onBlur={handleTitleBlur}
                autoFocus></input>
            ) : (
              <h3 onClick={handleTitleClick}>{title}</h3>
            )}
          </div>
          <ResultSection
            results={results}
            setResults={setResults}
            handleSaveClick={handleSaveClick}
            editingText={editingText}
            setEditingText={setEditingText}
            handleResultClick={handleResultClick}
            handleDeletion={handleDeletion}
            onDragEnd={onDragEnd}
            setChatOpen={setChatOpen}
          />
        </div>
      </div>
    </div>
  );
};

export default Translator;
