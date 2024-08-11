import React from "react";

const OutputSection = ({ outputs, handleSelection }) => {
  return (
    <div className="outputDiv">
      {outputs.map((output, index) => (
        <div className="output" key={index} onClick={() => handleSelection(output)}>
          {output.content}
        </div>
      ))}
    </div>
  );
};

export default OutputSection;
