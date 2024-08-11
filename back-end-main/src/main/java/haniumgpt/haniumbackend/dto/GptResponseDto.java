package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.GptEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GptResponseDto {
    private String content;

    @Builder
    public GptResponseDto(final GptEntity entity) {
        this.content = entity.getContent();
    }

    // content 설정하는 함수
    public void setContent(String content) {
        this.content = content;
    }

    // content 값을 가져오는 겟터 함수
    public String getContent() {
        return content;
    }
}