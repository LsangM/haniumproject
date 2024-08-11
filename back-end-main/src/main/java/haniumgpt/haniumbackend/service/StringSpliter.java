package haniumgpt.haniumbackend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static haniumgpt.haniumbackend.controller.TestStringSplit.sendGPT;

public class StringSpliter {
    public static List<String> Split(List<String> paragraphs, String key) {
        List<String> unp = new ArrayList<>();

        for (String paragraph : paragraphs) {
            List<String> temp = new ArrayList<>(Arrays.asList(paragraph.split(key, -1)));

            if (key.equals("\\.")) {
                for (int j = 1; j < temp.size(); j++) {
                    temp.set(j - 1, temp.get(j - 1) + ".");
                }
            }

            unp.addAll(temp);
        }

        return unp;
    }
    public static List<String> SplitString(String str) {

        List<String> paragraphs = new ArrayList<>(Arrays.asList(str.split("\n\n", -1)));

        paragraphs=Split(paragraphs, "\\n\\n");
        paragraphs=Split(paragraphs, "\\\\n\\\\n");
        paragraphs=Split(paragraphs, "\n");
        paragraphs=Split(paragraphs, "\\n");
        paragraphs=Split(paragraphs, "\\\\n");
        paragraphs=Split(paragraphs, "\\. ");

//        for (int i=0; i<paragraphs.size() && paragraphs.get(i).length()>200; i++) {
//            String Solong =paragraphs.get(i);
//            paragraphs.remove(i);
//            //기준 길이를 초과하는 문자열 Solong은 GPT에게 개행문자를 삽입하도록 요청. 그 후 개행문자가 추가된 문자열로 Split을 재귀적으로 호출
//            paragraphs.addAll(i, SplitString(TestStringSplit.sendGPT(Solong)));
//            i--;
//        }

        return paragraphs;
    }

}