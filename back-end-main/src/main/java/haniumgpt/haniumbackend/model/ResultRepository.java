package haniumgpt.haniumbackend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity,String>{
    List<ResultEntity> findByWorkEntity(WorkEntity workEntity);
    //ResultEntity findByIndex(int index);
    //List<ResultEntity> findByWorkId(String workId);
    List<ResultEntity> findByWorkEntityOrderByIndexAsc(WorkEntity workEntity);
}
