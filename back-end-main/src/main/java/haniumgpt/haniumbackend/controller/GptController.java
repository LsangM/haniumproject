package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.GptRequestDto;
import haniumgpt.haniumbackend.dto.GptResponseDto;
import haniumgpt.haniumbackend.dto.TransRequestDto;
import haniumgpt.haniumbackend.dto.TransResponseDto;
import haniumgpt.haniumbackend.model.GptEntity;
import haniumgpt.haniumbackend.model.WorkEntity;
import haniumgpt.haniumbackend.model.WorkRepository;
import haniumgpt.haniumbackend.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class GptController {
    @Autowired
    final GptService service; // 대화 기록 메세지 서비스
    @Autowired
    private WorkRepository workRepository;

    @PostMapping("/TranslationBuilderTest")
    //TransRequestDto에 들어갈 정보: GptRequestDto(role, content), transContext(상황정보 테이블), workId
    public TransResponseDto translatorController(@Valid @RequestBody TransRequestDto request) {

        String transContext = request.getTransContext();
        String workId = request.getWorkId();

        Optional<WorkEntity> optionalWorkEntity = workRepository.findById(workId);
        WorkEntity workEntity = optionalWorkEntity.get();
        if (optionalWorkEntity.isPresent()) {
            workEntity = optionalWorkEntity.get();
            // 작업 수행
        } else {
            // 해당 workId로 WorkEntity를 찾을 수 없음
        }

        GptRequestDto Gptrequest = request.getGptRequestDto();

        //sendGPT 이전에 목적에 맞게 프롬프트 생성
        List<GptRequestDto> prompt;
        String systemMessage;
        if (transContext.isBlank()) {
            systemMessage = "You are a English translator that translates Korean into English. " +
                            "Only response with translated result.";
        }
        else{
            systemMessage = "You are a translator that translates Korean into English, taking the following content into consideration " +
                            "and only response with translated result." + " For translation reference: " + transContext;
        }

        if(workEntity.getResultEntities().size()==0){
            prompt = GptService.buildNewPrompt(systemMessage);
        }
        else{
            prompt = workEntity.toGptRequestPrompt();
            if (prompt.size() >= 2) {
                prompt = prompt.subList(prompt.size() - 2, prompt.size());
            } else {
                // 리스트 크기가 2보다 작을 경우의 처리
                prompt = prompt.subList(0, prompt.size());
            }
            // 가져온 프롬프트의 최신 시스템 메시지가 현재 gpt에게 요청할 시스템 메시지와 같은지 판단
            for(int i=prompt.size()-1; i<=0; i--){
                if(prompt.get(i).getRole().equals("system")){
                    if(prompt.get(i).getContent().equals(systemMessage)){
                        //같다면 중복해서 추가할 필요 없이 작업을 끝냄
                        break;
                    }
                    else{
                        //다르다면 업데이트된 시스템 메시지를 프롬프트의 말단에 추가
                        prompt.add(
                                new GptRequestDto(GptEntity.builder()
                                .role("system")
                                .content(systemMessage)
                                .build())
                                );
                    }
                }
            }
        }

        //request, 프롬프트, 답변갯수를 넣어 sendGPT 호출
        List<GptResponseDto> gptResponseDtoList = GptService.sendGPT(Gptrequest, prompt, 3);

//        PromptEntity newPromptEntity = new PromptEntity();
//        newPromptEntity.setPrompt(EngtoKor);
//        PromptEntity savedEntity = promptRepository.save(newPromptEntity);
//        String promptId = savedEntity.getId();

        TransResponseDto transResponseDto = TransResponseDto.builder()
                .gptResponseDtoList(gptResponseDtoList)
                .workId(workId)
                .systemMessage(systemMessage)
                .build();

        return transResponseDto;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
