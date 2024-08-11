package haniumgpt.haniumbackend.service;

import haniumgpt.haniumbackend.model.ResultEntity;
import haniumgpt.haniumbackend.model.ResultRepository;
import haniumgpt.haniumbackend.model.WorkEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private WorkListService workListService;

    // result entity를 userid를 찾아서 저장함
    public List<ResultEntity> create(final ResultEntity entity){
        validate(entity);
        int resultSize = resultRepository.findAll().size()+1;
        entity.setIndex(resultSize);
        resultRepository.save(entity);
        return retrieve(entity.getWorkEntity().getWorkId());
    }

    //순서까지 Asc로 정렬해서 반환해줌
    public List<ResultEntity> retrieve(final String workId){
        WorkEntity workEntity  = workListService.oneRetrieve(workId);
        List<ResultEntity> results = resultRepository.findByWorkEntityOrderByIndexAsc(workEntity);
        return results;
    }

    //content update
    public List<ResultEntity> update(final ResultEntity entity){
        validate(entity);
        //기존 저장된 repo를 찾음
        final Optional<ResultEntity> original  = resultRepository.findById(entity.getResultId());
        if(original.isPresent()){
            final ResultEntity result = original.get();
            result.setContent(entity.getContent());
            resultRepository.save(result);
        }
        return retrieve(entity.getWorkEntity().getWorkId());
    }

    // 순서 변경
    public List<ResultEntity> reorder(final ResultEntity entity,final int index){
        validate(entity);
        //  index변경하려는 resultentity resultid로 찾기
        final Optional<ResultEntity> original  = resultRepository.findById(entity.getResultId());

        if (original.isPresent()) {
            //변경하려는 result 값 할당
            final ResultEntity originalResult = original.get();
            //변경하려는 result delete
            final List<ResultEntity> results = delete(originalResult);
            //전체 result index 재할당
            for(ResultEntity result: results){
                if(result.getIndex()>=index){
                    result.setIndex(result.getIndex()+1);
                }
            }
            originalResult.setIndex(index);
            results.add(originalResult);
            resultRepository.saveAll(results);
        }
        return retrieve(entity.getWorkEntity().getWorkId());
    }


    public List<ResultEntity> delete(final ResultEntity entity){
        validate(entity);
        try{
            resultRepository.delete(entity);
            List<ResultEntity> results = resultRepository.findByWorkEntity(entity.getWorkEntity());
            for(ResultEntity result : results){
                if (result.getIndex() > entity.getIndex()){
                    result.setIndex(result.getIndex()-1);
                }
            }
            resultRepository.saveAll(results);
        }catch (Exception e){
            throw new RuntimeException("error deleting entity"+entity.getResultId());
        }
        return retrieve(entity.getWorkEntity().getWorkId());
    }

    public void validate(final ResultEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getWorkEntity().getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}