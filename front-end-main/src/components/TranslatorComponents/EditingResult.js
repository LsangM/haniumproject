import React from "react";

const EditingResult = ({ editingText, handleSaveClick, handleChange }) => {
  return (
    <div className="editigDiv">
      <textarea value={editingText} onChange={handleChange}></textarea><br />
      <button className="saveButton" onClick={handleSaveClick}>저장</button>
    </div>
  );
};

export default EditingResult;