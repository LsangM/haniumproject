package haniumgpt.haniumbackend.service;

import haniumgpt.haniumbackend.model.WorkEntity;
import haniumgpt.haniumbackend.model.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkListService {
    @Autowired
    private WorkRepository workRepository;

    //신규 작업 생성
    public List<WorkEntity> create(final WorkEntity entity){
        validate(entity);
        workRepository.save(entity);
        return retrieve(entity.getUserId());
    }

    public List<WorkEntity> retrieve(final String userId){
        return workRepository.findByUserId(userId);
    }

    public WorkEntity oneRetrieve(final String workId){
        return workRepository.findById(workId).orElse(null);
    }

    public List<WorkEntity> update(final WorkEntity entity) {
        validate(entity);

        final Optional<WorkEntity> original = workRepository.findById(entity.getWorkId());
        if (original.isPresent()) {
            final WorkEntity work = original.get();
            work.setTitle(entity.getTitle());
            workRepository.save(work);
        }
        return retrieve(entity.getUserId());
    }

    public void validate(final WorkEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<WorkEntity> delete(final WorkEntity entity) {
        validate(entity);
        try {
            workRepository.delete(entity);
        } catch (Exception e) {
            throw new RuntimeException("error deleting entity " + entity.getWorkId());
        }
        return retrieve(entity.getUserId());
    }
}
