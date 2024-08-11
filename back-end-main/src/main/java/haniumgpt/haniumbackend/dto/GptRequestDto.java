package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.GptEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor
@Data
public class GptRequestDto {
    private String role;
    @NotNull(message = "번역할 내용을 입력하세요.")
    @Length(min = 1, max = 1000, message = "번역은 1회에 1000자까지 가능합니다.")
    private String content;
    @Builder
    public GptRequestDto(final GptEntity entity){
        this.role=entity.getRole();
        this.content = entity.getContent();
    }
    public GptEntity toEntity(){
        return GptEntity.builder()
                .role(this.getRole())
                .content(this.getContent())
                .build();
    }
    // role 설정하는 함수
    public void setRole(String role) {
        this.role = role;
    }

    // content 설정하는 함수
    public void setContent(String content) {
        this.content = content;
    }
}
