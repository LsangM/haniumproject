package haniumgpt.haniumbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class FeedbackEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동생성
    @GenericGenerator(name="system-uuid", strategy = "uuid") //문자열 형태 사용
    private String id;
    private String feedback;
    private String reTranslation;
    private String contentHead;
    private String contentTale;
    private int index;

    @ManyToOne
    @JoinColumn(name="resultId")
    private ResultEntity resultEntity;
}
