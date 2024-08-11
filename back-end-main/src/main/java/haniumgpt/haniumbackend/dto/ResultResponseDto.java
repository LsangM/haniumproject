package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.ResultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultResponseDto {
    private int index;
    private String workId;
    private String resultId;
    private String content;
    private String original;
    private String systemMessage;

    public ResultResponseDto(ResultEntity entity){
        this.index = entity.getIndex();
        this.workId = entity.getWorkEntity().getWorkId();
        this.resultId=entity.getResultId();
        this.content = entity.getContent();
        this.original = entity.getOriginal();
        this.systemMessage = entity.getSystemMessage();
    }
}
