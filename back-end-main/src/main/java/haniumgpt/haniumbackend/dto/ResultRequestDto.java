package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.ResultEntity;
import haniumgpt.haniumbackend.model.WorkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor
@Data
public class ResultRequestDto {
    private WorkEntity work;
    private String resultId;
    private String content;
    private String original;
    private String systemMessage;
    private int index;


    public ResultRequestDto(final ResultEntity entity){
        this.resultId=entity.getResultId();
        this.work=entity.getWorkEntity();
        this.content = entity.getContent();
        this.original = entity.getOriginal();
        this.systemMessage = entity.getSystemMessage();
        this.index = entity.getIndex();
    }

    public static ResultEntity toEntity(final ResultRequestDto dto){
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResultId(dto.getResultId());
        resultEntity.setWorkEntity(dto.getWork());
        resultEntity.setIndex(dto.getIndex());
        resultEntity.setContent(dto.getContent());
        resultEntity.setOriginal(dto.getOriginal());
        resultEntity.setSystemMessage(dto.getSystemMessage());

        return resultEntity;
    }



}
