package haniumgpt.haniumbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import haniumgpt.haniumbackend.dto.GptRequestDto;
import haniumgpt.haniumbackend.dto.GptResponseDto;
import haniumgpt.haniumbackend.model.GptEntity;
import haniumgpt.haniumbackend.model.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GptService {
    static String url = "https://api.openai.com/v1/chat/completions";
    static String apiKey = "Your OpenAI API key";
    static String model = "gpt-3.5-turbo"; // chatGPT API 모델

    static List<GptRequestDto> messages; // Gpt와의 통신을 위한 List

    //프롬프트 생성 함수. 시스템 메시지만 입력으로 넣으면 됨.
    public static List<GptRequestDto> buildNewPrompt(String command){
        List<GptRequestDto> messageSet = new ArrayList<>();
        GptRequestDto systemMessage = new GptRequestDto("system", command);
        messageSet.add(systemMessage);
        return messageSet;
    }

    public static List<GptRequestDto> buildGptRequestPromptFromResultEntities(List<ResultEntity> resultEntities) {
        List<GptRequestDto> prompt = new ArrayList<>();

        //시스템 메시지부터 프롬프트 첫부분에 쓰기
        String systemMessage = resultEntities.get(0).getSystemMessage();
        GptRequestDto dto = new GptRequestDto(GptEntity.builder()
                .role("system")
                .content(systemMessage)
                .build());
        prompt.add(dto);

        for (ResultEntity result : resultEntities) {
            if(!result.getSystemMessage().equals(systemMessage)){
                //상황 정보 테이블의 변경으로 시스템 메시지가 달라진 부분에선 시스템 메시지를 다시 써서 추가해줌
                systemMessage = result.getSystemMessage();
                GptRequestDto temp = new GptRequestDto(GptEntity.builder()
                        .role("system")
                        .content(systemMessage)
                        .build());
                prompt.add(temp);
            }
            //유저의 메시지 쓰기
            dto = new GptRequestDto(GptEntity.builder()
                    .role("user")
                    .content(result.getOriginal())
                    .build());
            prompt.add(dto);
            //어시스턴트의 번역 결과 쓰기
            dto = new GptRequestDto(GptEntity.builder()
                    .role("assistant")
                    .content(result.getContent())
                    .build());
            prompt.add(dto);
        }
        return prompt;
    }

    public static List<GptResponseDto> sendGPT(GptRequestDto request, List<GptRequestDto> messageSet/*프롬프트*/, int repeatNum) {
        try {
            request.setRole("user");
            //호출 시 제공된 프롬프트로 갈아입고 시작
            messages = messageSet;
            // HTTP POST request 생성
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // request body 빌드
            String body = buildRequestBody(request, repeatNum);

            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(body);
            writer.flush();
            writer.close();

            // OpenAI 로부터 답장을 받는다
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // ChatGPT API로부터 받은 response에서 content를 추출한다.(List 형식으로 바꾸어줌)
            //List responseContent = extractContentFromResponse(response.toString());

            // 최종적으로 추출된 답장을 리턴한다.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ChatGPT로부터 받은 응답에서 content만 추출하는 메서드
    public static List<GptResponseDto> extractContentFromResponse(String response) {
        //List<MessageList> extractResponse = new ArrayList<>();
        List<GptResponseDto> extractResponse = new ArrayList();
        //추출한 response(String)을 JSON 형식으로 변환한다.
        JSONObject jsonObject = new JSONObject(response);

        //"content"가 JSON에서 배열 형식이기에 Array로 받아준다.
        JSONArray choicesArray = new JSONArray(jsonObject.getJSONArray("choices"));

        //반복문을 통해 "message"의 JSON을 받아온 다음 "content"의 string을 받아와 List에 저장한다.
        for (int i = 0; i < choicesArray.length(); i++) {
            JSONObject jsonObject1 = choicesArray.getJSONObject(i);
            JSONObject mjsonObject = jsonObject1.getJSONObject("message");
            //extractResponse.add(new MessageList(i+1,mjsonObject.getString("content")));
            //extractResponse.add(mjsonObject.getString("content"));

            GptResponseDto responseDto = new GptResponseDto(GptEntity.builder()
                    .content(mjsonObject.getString("content"))
                    .build());

            extractResponse.add(responseDto);
        }
        return extractResponse;
    }

    public static String buildRequestBody(GptRequestDto request, int repeatNum) throws JsonProcessingException { //n은 받고싶은 답변의 갯수
        // 현재 송신할 사용자 메시지 채팅 기록에 추가
        messages.add(request);

        // 이전 메시지가 모두 담긴 messages 리스트에서 채팅 기록을 추출해 request body에 추가
        // +) 여기부분을 이해하면 MessageRequestDto로 통합하여 사용할 예정(Message class 사용에서 탈출하고자 함!!)
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{\"model\": \"").append(model).append("\", \"messages\": [");
        for (GptRequestDto m : messages) {
            // {"role": "m.getRole()"},{"content": "m.getContent()"},
            requestBody
                    .append("{\"role\": \"")
                    .append(m.getRole())
                    .append("\", \"content\": ")
                    .append(mapper.writeValueAsString(m.getContent()))
                    .append("}, ");
        }
        // 마지막 콤마와 공백 제거.
        if (requestBody.length() >= 2) {
            requestBody.delete(requestBody.length() - 2, requestBody.length());
        }
        // 답변 받을 횟수까지 추가 후 닫아주기
        requestBody.append("],\"n\":" + repeatNum + "}");

        // 번역에서는 모든 채팅 기록을 유지할 필요가 없어서 현재 메시지 다시 제거
        messages.remove(messages.size() - 1);
        return requestBody.toString();
    }
}