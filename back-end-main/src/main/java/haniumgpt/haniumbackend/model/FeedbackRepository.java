package haniumgpt.haniumbackend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {
    List<FeedbackEntity> findByResultEntity(ResultEntity resultEntity);
    List<FeedbackEntity> findByResultEntityOrderByIndexAsc(ResultEntity resultEntity);
}
