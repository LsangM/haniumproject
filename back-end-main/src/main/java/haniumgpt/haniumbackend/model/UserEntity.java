package haniumgpt.haniumbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동생성
    @GenericGenerator(name="system-uuid", strategy = "uuid") //문자열 형태 사용
    private String id;
    @Column
    private String email;
    @Column
    private String password;
    @Column(nullable = true)
    private String job;
    @Column(nullable = true)
    private String interestedField;
    @Column(nullable = true)
    private String englishProficiency;
}
