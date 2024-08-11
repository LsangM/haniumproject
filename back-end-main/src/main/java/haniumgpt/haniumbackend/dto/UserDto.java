package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String id;
    private String token;
    private String job;
    private String interestedField;
    private String englishProficiency;

    @Builder
    public UserDto(final UserEntity entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.job = entity.getJob();
        this.interestedField = entity.getInterestedField();
        this.englishProficiency = entity.getEnglishProficiency();
    }
    public UserDto(final UserEntity entity, final String token){
        this(entity);
        this.token=token;
    }
    public static UserEntity toEntity(final UserDto dto){
        return UserEntity.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword()) // entity->dto는 안된
                .job(dto.getJob())
                .interestedField(dto.getInterestedField())
                .englishProficiency(dto.getEnglishProficiency())
                .build();
    }
}
