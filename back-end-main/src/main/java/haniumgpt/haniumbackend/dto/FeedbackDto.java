package haniumgpt.haniumbackend.dto;

import haniumgpt.haniumbackend.model.FeedbackEntity;
import haniumgpt.haniumbackend.model.ResultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private String id;
    private String feedback;
    private String reTranslation;
    private String contentHead;
    private String contentTale;
    private String resultId;  // ResultEntity의 ID를 저장
    private int index;

    public static FeedbackDto fromEntity(FeedbackEntity feedbackEntity) {
        return FeedbackDto.builder()
                .id(feedbackEntity.getId())
                .feedback(feedbackEntity.getFeedback())
                .reTranslation(feedbackEntity.getReTranslation())
                .contentHead(feedbackEntity.getContentHead())
                .contentTale(feedbackEntity.getContentTale())
                .resultId(feedbackEntity.getResultEntity().getResultId())
                .index(feedbackEntity.getIndex())
                .build();
    }

    public FeedbackEntity toEntity(ResultEntity resultEntity) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setId(this.id);
        feedbackEntity.setFeedback(this.feedback);
        feedbackEntity.setReTranslation(this.reTranslation);
        feedbackEntity.setContentHead(this.contentHead);
        feedbackEntity.setContentTale(this.contentTale);
        feedbackEntity.setResultEntity(resultEntity);
        feedbackEntity.setIndex(this.index);
        return feedbackEntity;
    }
}
