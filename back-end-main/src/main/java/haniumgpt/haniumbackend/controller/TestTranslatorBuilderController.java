package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.GptRequestDto;
import haniumgpt.haniumbackend.dto.GptResponseDto;
import haniumgpt.haniumbackend.dto.ResponseDto;
import haniumgpt.haniumbackend.dto.ResultRequestDto;
import haniumgpt.haniumbackend.model.ResultEntity;
import haniumgpt.haniumbackend.service.GptService;
import haniumgpt.haniumbackend.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TestTranslatorBuilderController {
    // Message class 대신에 사용
   /* @Autowired
    final MessageService service; // 대화 기록 메세지 서비스
    @Autowired
    final ResultService resultservice; // 결과 기록 result 서비스

    @PostMapping("/TranslationBuilderTest")
    public List translatorController(@Valid @RequestBody GptRequestDto request) {
        //sendGPT 이전에 목적에 맞게 프롬프트 생성
        List<GptRequestDto> EngtoKor = GptService.buildPrompt("You are a English translator that translates other languages into English. Only response with translated result.");

        //request, 프롬프트, 답변갯수를 넣어 sendGPT 호출
        List<GptResponseDto> responseDtos = GptService.sendGPT(request, EngtoKor, 3);

        //sendGPT의 반환 형식을 MessageResponseDto의 List로 바꿨으므로, 컨텐츠 부분을 문자열로 추출해야함
        List<String> responseStrings = new ArrayList<String>();
        for(int i=0; i<responseDtos.size(); i++){
            responseStrings.add(responseDtos.get(i).getContent());
        }

        return responseStrings;
    }

    @GetMapping("/api/results/saveAll")
    public ResponseEntity<?> retrieveResultList(@AuthenticationPrincipal String userId) {
        List<ResultEntity> entities = resultservice.retrieve(userId);
        List<ResultRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultRequestDto> responseDto = new ResponseDto<>();
        //responseDto.setTitle();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/api/results/saveAll")
    public ResponseEntity<ResponseDto> createResult(@AuthenticationPrincipal String userId, @RequestBody ResultRequestDto dto) {
        try {
            //dto의 title, content를 entity로 변경
            ResultEntity entity = ResultRequestDto.toEntity(dto);
            //id를 null로 UserId를 userId로 set
            entity.setId(null);
            entity.setUserId(userId);
            //create 진행
            List<ResultEntity> entities = resultservice.create(entity);
            // entity를 dto list(id,title,content)로 변경
            List<ResultRequestDto> dtos = convertToDtoListFromEntityList(entities);
            // responseDto에 data 저장
            ResponseDto<ResultRequestDto> response = new ResponseDto<>();
            response.setData(dtos);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto<ResultRequestDto> response = new ResponseDto<>();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/api/results/saveAll")
    public ResponseEntity<ResponseDto> updateResult(@AuthenticationPrincipal String userId, @RequestBody ResultRequestDto dto) {
        // userId set
        ResultEntity entity = ResultRequestDto.toEntity(dto);
        entity.setUserId(userId);
        //
        List<ResultEntity> entities = resultservice.update(entity);
        List<ResultRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultRequestDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }


    @PutMapping("/api/results/saveAlltest")
    public ResponseEntity<ResponseDto> reOrder(@AuthenticationPrincipal String userId, @RequestBody List<ResultRequestDto> listDto){
        for(ResultRequestDto dto : listDto){
            ResultEntity entity = ResultRequestDto.toEntity(dto);
            //entity.setUserId(userId);
            resultservice.orderUpdate(entity);
        }
        List<ResultRequestDto> dtos = convertToDtoListFromEntityList(resultservice.retrieve(userId));
        ResponseDto<ResultRequestDto> responseDto = new ResponseDto<>();
        responseDto.setData(dtos);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/api/results/saveAll")
    public ResponseEntity<ResponseDto> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody ResultRequestDto dto) {
        ResultEntity entity = ResultRequestDto.toEntity(dto);
        entity.setUserId(userId);

        List<ResultEntity> entities = resultservice.delete(entity);
        List<ResultRequestDto> dtos = convertToDtoListFromEntityList(entities);

        ResponseDto<ResultRequestDto> responseDto = ResponseDto.<ResultRequestDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDto);
    }
    //리턴된 엔티티 리스트를 resultDto 리스트로 변환한다.
    private List<ResultRequestDto> convertToDtoListFromEntityList(List<ResultEntity> from) {
        return from.stream().map(ResultRequestDto::new).collect(Collectors.toList());
    }

*/
}
