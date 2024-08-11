package haniumgpt.haniumbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //기본 생성자 자동 생성
@Data
public class TransRequestDto {
    private GptRequestDto gptRequestDto;
    private String transContext; //번역 참고정보(상황정보 테이블)
    private String workId;

    @Builder
    public TransRequestDto(final GptRequestDto gptRequestDto, final String transContext) {
        this.gptRequestDto = gptRequestDto;
        this.transContext = transContext;
        this.workId = workId;
    }

    // gptRequestDto 설정하는 함수
    public void setGptRequestDto(GptRequestDto gptRequestDto) {
        this.gptRequestDto = gptRequestDto;
    }

    // 번역 참고정보 설정하는 함수
    public void setTransContext(String transContext) {
        this.transContext = transContext;
    }

    // workId 설정하는 함수
    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
