import React, { useEffect, useState } from "react";
import { DragDropContext, Droppable } from "react-beautiful-dnd";
import EditingResult from "./EditingResult";
import DisplayResult from "./DisplayResult";

const ResultSection = ({ results, setResults, handleSaveClick, editingText, setEditingText, handleResultClick, handleDeletion, setChatOpen, onDragEnd }) => {

    const [tooltipContent, setTootipContent] = useState(""); // 화면에 표시할 번역 원문

    // 번역 원문을 툴팁으로 표시하기 위한 부분
    const handleMouseEnter = (result, targetIndex) => {
        setTootipContent(result.original)

        setResults((prevResults) => {
            const updatedResults = prevResults.map((result) => ({ ...result, showTooltip: false }));
            return updatedResults;
        });

        setResults((prevResults) => {
            const updatedResults = [...prevResults];
            updatedResults[targetIndex] = { ...updatedResults[targetIndex], showTooltip: true };
            return updatedResults;
        });
    };

    const handleMouseLeave = (targetIndex) => {
        setResults((prevResults) => {
            const updatedResults = [...prevResults];
            updatedResults[targetIndex] = { ...updatedResults[targetIndex], showTooltip: false };
            return updatedResults;
        });
    };

    return (
        <DragDropContext onDragEnd={onDragEnd}>
            <Droppable droppableId="results">
                {(provided) => (
                    <div {...provided.droppableProps} ref={provided.innerRef}>
                        {results.map((result, index) =>
                            result.editing ? (
                                <EditingResult
                                    editingText={editingText}
                                    handleSaveClick={() => handleSaveClick(result.resultId)}
                                    handleChange={(e) => setEditingText(e.target.value)}
                                />
                            ) : (
                                <DisplayResult
                                    result={result}
                                    index={index}
                                    handleResultClick={() => handleResultClick(result.content, index)}
                                    handleDeletion={() => handleDeletion(result)}
                                    handleMouseEnter={() => handleMouseEnter(result, index)}
                                    handleMouseLeave={() => { handleMouseLeave(index) }}
                                    tooltipContent={tooltipContent}
                                    setChatOpen={setChatOpen}
                                />
                            )
                        )}
                        {provided.placeholder}
                    </div>
                )}
            </Droppable>
        </DragDropContext>
    );
};

export default ResultSection;
