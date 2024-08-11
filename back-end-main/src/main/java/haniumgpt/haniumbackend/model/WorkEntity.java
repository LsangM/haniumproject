package haniumgpt.haniumbackend.model;

import haniumgpt.haniumbackend.dto.GptRequestDto;
import haniumgpt.haniumbackend.service.GptService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work")
@ToString(exclude = "resultEntities")
public class WorkEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동생성
    @GenericGenerator(name="system-uuid", strategy = "uuid") //문자열 형태 사용
    private String workId;
    private String userId;
    private String title;
    private String type;
    //private String currentSystemMessage;

    @CreationTimestamp
    private Timestamp date;

    // WorkEntity가 여러 개의 ResultEntity를 가질 수 있도록 설정
    @OneToMany(mappedBy = "workEntity", cascade = CascadeType.ALL)
    private List<ResultEntity> resultEntities;

    public List<GptRequestDto> toGptRequestPrompt() {
        return GptService.buildGptRequestPromptFromResultEntities(resultEntities);
    }
}
