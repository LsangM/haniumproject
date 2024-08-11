package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.ReorderRequestDto;
import haniumgpt.haniumbackend.dto.ResponseDto;
import haniumgpt.haniumbackend.dto.ResultRequestDto;
import haniumgpt.haniumbackend.dto.ResultResponseDto;
import haniumgpt.haniumbackend.model.ResultEntity;
import haniumgpt.haniumbackend.service.ResultService;
import haniumgpt.haniumbackend.service.WorkListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
public class ResultController {
    @Autowired
    final ResultService resultservice; // 결과 기록 result 서비스

    @Autowired
    final WorkListService workListService;

    @GetMapping("/results/{workId}")
    public ResponseEntity<?> retrieveResultList(@PathVariable("workId") String workId) {
        List<ResultEntity> entities = resultservice.retrieve(workId);
        List<ResultResponseDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultResponseDto> responseDto = new ResponseDto<>();
        //responseDto.setTitle();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/results/{workId}")
    //ResultRequestDto에 들어갈 정보: workId, content, original, systemMessage
    public ResponseEntity<ResponseDto> createResult(@PathVariable("workId") String workId,@RequestBody ResultRequestDto dto) {
        try {
            //dto의 title, content를 entity로 변경
            ResultEntity entity = ResultRequestDto.toEntity(dto);
            //id를 null로 UserId를 userId로 set
            entity.setResultId(null);

            entity.setWorkEntity(workListService.oneRetrieve(workId));
            //create 진행
            List<ResultEntity> entities = resultservice.create(entity);
            // entity를 dto list(id,title,content)로 변경
            List<ResultResponseDto> dtos = convertToDtoListFromEntityList(entities);
            // responseDto에 data 저장
            ResponseDto<ResultResponseDto> response = new ResponseDto<>();
            response.setData(dtos);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<ResultRequestDto> response = new ResponseDto<>();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/results/{workId}")
    public ResponseEntity<ResponseDto> updateResult(@PathVariable("workId") String workId, @RequestBody ResultRequestDto dto) {
        // workId set
        ResultEntity entity = ResultRequestDto.toEntity(dto);
        entity.setWorkEntity(workListService.oneRetrieve(workId));

        List<ResultEntity> entities = resultservice.update(entity);
        List<ResultResponseDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultResponseDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/results/reorder/{workId}")
    public ResponseEntity<ResponseDto> reorderResult(@PathVariable("workId") String workId, @RequestBody ReorderRequestDto dto) {

        // workId set
        ResultEntity entity=new ResultEntity();
        entity.setWorkEntity(workListService.oneRetrieve(workId));
        entity.setResultId(dto.getResultId());

        List<ResultEntity> entities = resultservice.reorder(entity,dto.getIndex());
        List<ResultResponseDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultResponseDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

/* 수정해야함.
    @PutMapping("/api/results/saveAlltest")
    public ResponseEntity<ResponseDto> reOrder(@AuthenticationPrincipal String userId, @RequestBody List<ResultRequestDto> listDto){
        for(ResultRequestDto dto : listDto){
            ResultEntity entity = ResultRequestDto.toEntity(dto);
            //entity.setUserId(userId);
            //resultservice.orderUpdate(entity);
        }
        List<ResultRequestDto> dtos = convertToDtoListFromEntityList(resultservice.retrieve(userId));
        ResponseDto<ResultRequestDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }
*/
    @PutMapping("/results/delete/{workId}")
    public ResponseEntity<ResponseDto> deleteTodo(@PathVariable("workId") String workId, @RequestBody ResultRequestDto dto) {
        ResultEntity entity = ResultRequestDto.toEntity(dto);
        entity.setWorkEntity(workListService.oneRetrieve(workId));

        List<ResultEntity> entities = resultservice.delete(entity);
        List<ResultResponseDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultResponseDto> responseDto = ResponseDto.<ResultResponseDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDto);
    }
    //리턴된 엔티티 리스트를 resultDto 리스트로 변환한다.
    private List<ResultResponseDto> convertToDtoListFromEntityList(List<ResultEntity> from) {
        return from.stream().map(ResultResponseDto::new).collect(Collectors.toList());
    }
}
