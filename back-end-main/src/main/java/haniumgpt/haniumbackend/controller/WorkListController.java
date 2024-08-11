package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.ResponseDto;
import haniumgpt.haniumbackend.dto.ResultRequestDto;
import haniumgpt.haniumbackend.dto.WorkListRequestDto;
import haniumgpt.haniumbackend.model.WorkEntity;
import haniumgpt.haniumbackend.service.WorkListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class WorkListController {
    @Autowired
    private WorkListService workListService;

    // 작업 목록 조회
    @GetMapping("/worklist")
    public ResponseEntity<?> retrieveWorkList(@AuthenticationPrincipal String userId) {

        List<WorkEntity> entities = workListService.retrieve(userId);
        List<WorkListRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<WorkListRequestDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    // 새 작업 생성
    @PostMapping("/worklist")
    public ResponseEntity<?> createWork(@AuthenticationPrincipal String userId,@RequestBody WorkListRequestDto requestDto) {
        try {
            WorkEntity entity = WorkListRequestDto.toEntity(requestDto);
            entity.setWorkId(null);
            entity.setUserId(userId);
            List<WorkEntity> entities = workListService.create(entity);
            List<WorkListRequestDto> dtos = convertToDtoListFromEntityList(entities);
            ResponseDto<WorkListRequestDto> response = new ResponseDto<>();
            response.setData(dtos);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<ResultRequestDto> response = new ResponseDto<>();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 작업 삭제
    @DeleteMapping("/worklist")
    public ResponseEntity<?> deleteWork(@AuthenticationPrincipal String userId, @RequestBody WorkListRequestDto dto) {
        WorkEntity entity = WorkListRequestDto.toEntity(dto);
        entity.setUserId(userId);

        List<WorkEntity> entities = workListService.delete(entity);
        List<WorkListRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<WorkListRequestDto> responseDto = ResponseDto.<WorkListRequestDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDto);
    }


    // 작업 수정
    @PutMapping("/worklist")
    public ResponseEntity<?> updateWork(@AuthenticationPrincipal String userId, @RequestBody WorkListRequestDto dto) {
        // userId set
        WorkEntity entity = WorkListRequestDto.toEntity(dto);
        entity.setUserId(userId);

        List<WorkEntity> entities = workListService.update(entity);
        List<WorkListRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<WorkListRequestDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    private List<WorkListRequestDto> convertToDtoListFromEntityList(List<WorkEntity> from) {
        return from.stream().map(WorkListRequestDto::new).collect(Collectors.toList());
    }
}
