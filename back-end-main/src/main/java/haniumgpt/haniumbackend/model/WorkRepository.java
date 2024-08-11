package haniumgpt.haniumbackend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, String> {
    List<WorkEntity> findByUserId(String userId);
}
