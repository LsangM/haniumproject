package haniumgpt.haniumbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransResponseDto {
    private List<GptResponseDto> gptResponseDtoList;
    private String workId;
    private String systemMessage;

    // gptResponseDtoList 설정하는 함수
    public void setGptResponseDtoList(List<GptResponseDto> gptResponseDtoList) {
        this.gptResponseDtoList = gptResponseDtoList;
    }

    // gptResponseDtoList 값을 가져오는 겟터 함수
    public List<GptResponseDto> getGptResponseDtoList() {
        return gptResponseDtoList;
    }

    // promptId 설정하는 함수
    public void setPromptId(String promptId) {
        this.workId = promptId;
    }

    // promptId 값을 가져오는 겟터 함수
    public String getPromptId() {
        return workId;
    }

    // systemMessage 설정하는 함수
    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    // systemMessage 값을 가져오는 겟터 함수
    public String getSystemMessage() {
        return systemMessage;
    }
}
