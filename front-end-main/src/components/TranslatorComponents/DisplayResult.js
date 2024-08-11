import React, { useState } from "react";
import { Draggable } from "react-beautiful-dnd";

const DisplayResult = ({ result, index, handleResultClick, handleDeletion, handleMouseEnter, handleMouseLeave, tooltipContent, setChatOpen }) => {

  const [tooltipHovered, setTooltipHoverd] = useState(false);

  const handleTooltipMouseEnter = () => {
    setTooltipHoverd(true);
  };

  const handleTooltipMouseLeave = () => {
    setTooltipHoverd(false);
  };
  
  // 피드백 챗봇 버튼 클릭 시 실행 함수
  const handleChatButtonClick = (resultId) => {
    console.log("ChatBot Opened, resultId: ", resultId);
    setChatOpen(true);
  }

  return (
    <Draggable key={index} draggableId={index.toString()} index={index}>
      {(provided) => (
        <div
          className="result"
          {...provided.draggableProps}
          
          ref={provided.innerRef}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        >
          <div className="resultContent">{result.content}</div>
          <div className="resultButtons">
            <div className="resultHandle" {...provided.dragHandleProps} >↕️</div>
            <div onClick={handleResultClick}>✏</div>
            <div onClick={() => {handleChatButtonClick(result.id)}}>💬</div>
            <div onClick={handleDeletion}>✕</div>
          </div>
          {result.showTooltip && (
            <div
              className="tooltip"
              onMouseEnter={handleTooltipMouseEnter}
              onMouseLeave={handleTooltipMouseLeave}
            >{tooltipContent}</div>)}
        </div>
      )}
    </Draggable>
  );
};

export default DisplayResult;