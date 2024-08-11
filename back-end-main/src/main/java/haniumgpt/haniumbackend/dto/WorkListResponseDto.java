package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.WorkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkListResponseDto {
    private String workId;
    private String title;
    private Timestamp date;
    //private String currentSystemMessage;

    @Builder
    public WorkListResponseDto(final WorkEntity entity) {
        this.workId = entity.getWorkId();
        this.title = entity.getTitle();
        this.date = entity.getDate();
        //this.currentSystemMessage = entity.getCurrentSystemMessage();
    }
}
