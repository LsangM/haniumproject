package haniumgpt.haniumbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "result")
@ToString(exclude = "workEntity")
public class ResultEntity{
    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동생성
    @GenericGenerator(name="system-uuid", strategy = "uuid") //문자열 형태 사용
    private String resultId;
    private String role;
    private String content;
    private String original;
    private String systemMessage;
    private int index;

    //외래키 설정
    @ManyToOne
    @JoinColumn(name="workId")
    private WorkEntity workEntity;
}
