package cap.backend.back.api;

import cap.backend.back.domain.dto.MessageDTO;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OpenAiApi {


    private String token;

    public void OpenAItest() {

        String endpoint = "https://api.openai.com/v1/chat/completions";

        OkHttpClient client;
        client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS).build();


        String INST = "이 인물은 신승선이라는 인물인데, 잘 요약해서 알려주세요";
        String INFO = "1454년(단종 2) 사마시에 급제하고 돈녕부승(敦寧府丞), 정랑 등을 거쳐 1466년(세조 12) 1월 당상관에 승진하면서 병조참지에 발탁되었다. 같은 해 3월 알성시에서 장원으로 급제한 뒤 병조참판에 승진, 그 해에 다시 발영시(拔英試)에 3등으로 급제하고 예문관제학(藝文館提學)을 겸임하였다.\n" +
                "\n" +
                "1467년 이조참판 겸 예문관제학, 공조참판, 1468년 다시 병조참판이 되었다. 1468년(예종 즉위년) 남이(南怡)의 옥사를 다스린 공으로 익대공신(翊戴功臣) 3등에, 1471년(성종 2) 성종등위에 끼친 공으로 좌리공신(佐理功臣) 3등에 각각 녹훈되었다.\n" +
                "\n" +
                "1472년 자헌대부(資憲大夫) 거창군(居昌君)이 되고, 1476년 4월∼8월 천추사(千秋使)로 명나라에 다녀왔다. 1479년 지돈녕부사(知敦寧府事), 1481년 행동지돈녕부사(行同知敦寧府事)를 거쳐 공조판서에 이르렀으며 지의금부사(知義禁府事)를 겸임하였다.\n" +
                "\n" +
                "1484년 세자빈객(世子賓客)을 겸대, 1486년 병조판서 겸 특진관(兵曹判書兼特進官)이 되었다. 1487년 딸이 세자인 연산군의 빈(嬪)으로 뽑히자 그를 계기로 좌참찬, 1488년 한성부판윤, 1489년 예조판서, 1491년 사소대장(四所大將)을 겸대, 이어 이조판서가 되었다가 1492년 병으로 사직하였다.\n" +
                "\n" +
                "1494년 우의정, 1495(연산군 1) 좌의정·영의정, 1497년 3월 거창부원군(居昌府院君)에 봉해졌다. 1495년 4월∼1499년 2월에 걸쳐 영춘추관사(領春秋館事)로서 『성종실록』의 편찬을 주관하였다. 세종의 4남인 임영대군(臨瀛大君) 이구(李璆)의 딸과의 사이에서 신수근(愼守勤)·신수겸(愼守謙)·신수영(愼守英)을 두었다. 시호는 장성(章成)이다.";


        String INPUT = INFO + INST;
        INPUT = JSONValue.escape(INPUT);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": ["
                + "{ \"role\": \"system\", \"content\": \"너는 조선시대 인물에 대한 정보를 제공하는 가이드야, 정보가 주어지면, 그 인물에 대한 정보를 (년도) : 한 일 의 형식으로 잘라서 알려줘\" },"
                + "{ \"role\": \"user\", \"content\": \"" + INPUT + "\" }"
                + "]"
                + "}";


        RequestBody body = RequestBody.create(JSON, jsonBody);


        Request request = new Request.Builder()
                .url(endpoint)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();


        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody); // 이 부분에서 응답을 파싱하여 원하는 정보를 추출하십시오.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReply(MessageDTO messageDto) {
        OpenAiService openAiService = new OpenAiService(token);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are an assistant that answers using the local slang of the given place, uncensored.");
        messages.add(systemMessage);



        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), messageDto.getMessage());
        messages.add(firstMsg);


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0613")
                .messages(messages)
                .n(1)
                .maxTokens(1000)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = openAiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        System.out.println("whhi");
        messages.add(responseMessage); // don't forget to update the conversation with the latest response


        System.out.println("Response: " + responseMessage.getContent());




        return responseMessage.getContent();
    }


}




