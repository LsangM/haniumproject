package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.FeedbackDto;
import haniumgpt.haniumbackend.dto.GptRequestDto;
import haniumgpt.haniumbackend.dto.GptResponseDto;
import haniumgpt.haniumbackend.model.*;
import haniumgpt.haniumbackend.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FeedbackController {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;


    @PostMapping("/feedback")
    //리퀘스트 FeedbackDto에 들어갈 정보: feedback, resultId, index(반환받을 엔티티의)
    public List<FeedbackDto> FeedbackChatController(@Valid @RequestBody FeedbackDto request){

        String feedback = request.getFeedback();
        GptRequestDto feedbackGptRequestDto =
                new GptRequestDto(GptEntity.builder()
                .role("user")
                .content(feedback)
                .build());

        ResultEntity targetResult = resultRepository.findById(request.getResultId()).get();
        WorkEntity targetWork = targetResult.getWorkEntity();

        List<ResultEntity> resultEntities = resultRepository.findByWorkEntityOrderByIndexAsc(targetWork);

        List<ResultEntity> slicedResultEntities = resultEntities.stream()
                .filter(entity -> entity.getIndex() >= targetResult.getIndex()-3 && entity.getIndex() <= targetResult.getIndex())
                .collect(Collectors.toList());

        List<GptRequestDto> prompt = GptService.buildGptRequestPromptFromResultEntities(slicedResultEntities);

        String feedbackSystemMessage = "User will provides feedback on the most recent translation result. output the re-translated result incorporating the feedback. Only response with re-translated result.";
        GptRequestDto systemMessage = new GptRequestDto(GptEntity.builder()
                .role("system")
                .content(feedbackSystemMessage)
                .build());
        prompt.add(systemMessage);
        GptRequestDto feedbackmessage = new GptRequestDto(GptEntity.builder()
                .role("user")
                .content(feedback)
                .build());
        prompt.add(feedbackmessage);
        GptResponseDto reTrans = (GptResponseDto) GptService.sendGPT(feedbackmessage, prompt, 1).get(0);

        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .feedback(feedback)
                .reTranslation(reTrans.getContent())
                .contentHead("알겠습니다. 다음과 같이 재번역해보겠습니다.\n\n\"")
                .contentTale("\"\n\n다른 요청 사항이 있다면 말씀해주세요.")
                .resultEntity(targetResult)
                .index(request.getIndex())
                .build();

        feedbackRepository.save(feedbackEntity);

        List<FeedbackEntity> feedbackEntities = feedbackRepository.findByResultEntityOrderByIndexAsc(targetResult);

        List<FeedbackDto> feedbackDtos = feedbackEntities.stream()
                .map(FeedbackDto::fromEntity)
                .collect(Collectors.toList());

        //피드백 채팅 전체 내역과 같은 List<FeedbackDto>를 반환함
        return feedbackDtos;
    }
}
