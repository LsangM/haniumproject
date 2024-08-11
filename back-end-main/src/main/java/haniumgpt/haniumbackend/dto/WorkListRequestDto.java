package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.WorkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkListRequestDto {
    private String userId;
    private String workId;
    private String type;
    private String title;

    public WorkListRequestDto(final WorkEntity entity){
        this.userId=entity.getUserId();
        this.workId = entity.getWorkId();
        this.title = entity.getTitle();
        this.type = entity.getType();
    }

    public static WorkEntity toEntity(final WorkListRequestDto dto){
        return WorkEntity.builder()
                .userId(dto.getUserId())
                .workId(dto.getWorkId())
                .title(dto.getTitle())
                .type(dto.getType())
                .build();
    }
}
