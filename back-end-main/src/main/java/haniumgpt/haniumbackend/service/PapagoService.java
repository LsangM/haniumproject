package haniumgpt.haniumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// 네이버 기계번역 (Papago SMT) API 예제
@Slf4j
@RequiredArgsConstructor
@Service
public class PapagoService {

    public static String sendPapago(String args) {
        String clientId = "y112bg1u1c";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "WPioeJFnMWCklCy8Os1DXL9Hf2BYby1dieK2P3Y7";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = args;
            JSONObject jsonObject1 = new JSONObject(text);
            text = URLEncoder.encode(jsonObject1.getString("content"), "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            String jsonString = response.toString();
            JSONObject jsonObject2 = new JSONObject(jsonString);
            String translation = jsonObject2.getJSONObject("message").getJSONObject("result").getString("translatedText");

            return translation;
        } catch (Exception e) {
            return e.toString();
        }
    }
//
//    static String clientId = "y112bg1u1c";//애플리케이션 클라이언트 아이디값";
//    static String clientSecret = "WPioeJFnMWCklCy8Os1DXL9Hf2BYby1dieK2P3Y7";//애플리케이션 클라이언트 시크릿값";
//
//    static String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
//    static String text;
//
//    //번역 호출 함수. String을 인자로 받아 번역한 후 String으로 반환함.
//    public static String sendPapago(String args){
//        try {
//            text = URLEncoder.encode(args, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("인코딩 실패", e);
//        }
//
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", clientId);
//        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
//
//        String responseBody = post(apiURL, requestHeaders, text);
//
//        JSONObject jsonObject = new JSONObject(responseBody);
//        String translation = jsonObject.getJSONObject("message").getJSONObject("result").getString("translatedText");
//
//        return translation;
//    }
//
//    private static String post(String apiUrl, Map<String, String> requestHeaders, String text){
//        HttpURLConnection con = connect(apiUrl);
//        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
//        try {
//            con.setRequestMethod("POST");
//            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
//                con.setRequestProperty(header.getKey(), header.getValue());
//            }
//
//            con.setDoOutput(true);
//            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
//                wr.write(postParams.getBytes());
//                wr.flush();
//            }
//
//            int responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
//                return readBody(con.getInputStream());
//            } else {  // 에러 응답
//                return readBody(con.getErrorStream());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("API 요청과 응답 실패", e);
//        } finally {
//            con.disconnect();
//        }
//    }
//
//    private static HttpURLConnection connect(String apiUrl){
//        try {
//            URL url = new URL(apiUrl);
//            return (HttpURLConnection)url.openConnection();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
//        } catch (IOException e) {
//            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
//        }
//    }
//
//    private static String readBody(InputStream body){
//        InputStreamReader streamReader = new InputStreamReader(body);
//
//        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
//            StringBuilder responseBody = new StringBuilder();
//
//            String line;
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            return responseBody.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
//        }
//    }
}