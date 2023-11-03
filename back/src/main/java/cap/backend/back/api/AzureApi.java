package cap.backend.back.api;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AzureApi {

    public void AzureAPItest() {
//        String azureOpenaiKey = "81ffb8af272b4a468f9eb8a7a3b5ae21";
//        String endpoint = "https://gpt4canadaletsgo.openai.azure.com/";


        String azureOpenaiKey = "81ffb8af272b4a468f9eb8a7a3b5ae21";
        String endpoint = "https://gpt4canadaletsgo.openai.azure.com/";
        String deploymentOrModelId = "MainModel_1";

        OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(azureOpenaiKey))
                .buildClient();

        String INFO="""
                1454년(단종 2) 사마시에 급제하고 돈녕부승(敦寧府丞), 정랑 등을 거쳐 1466년(세조 12) 1월 당상관에 승진하면서 병조참지에 발탁되었다. 같은 해 3월 알성시에서 장원으로 급제한 뒤 병조참판에 승진, 그 해에 다시 발영시(拔英試)에 3등으로 급제하고 예문관제학(藝文館提學)을 겸임하였다.
                1467년 이조참판 겸 예문관제학, 공조참판, 1468년 다시 병조참판이 되었다. 1468년(예종 즉위년) 남이(南怡)의 옥사를 다스린 공으로 익대공신(翊戴功臣) 3등에, 1471년(성종 2) 성종등위에 끼친 공으로 좌리공신(佐理功臣) 3등에 각각 녹훈되었다.
                1472년 자헌대부(資憲大夫) 거창군(居昌君)이 되고, 1476년 4월∼8월 천추사(千秋使)로 명나라에 다녀왔다. 1479년 지돈녕부사(知敦寧府事), 1481년 행동지돈녕부사(行同知敦寧府事)를 거쳐 공조판서에 이르렀으며 지의금부사(知義禁府事)를 겸임하였다.
                1484년 세자빈객(世子賓客)을 겸대, 1486년 병조판서 겸 특진관(兵曹判書兼特進官)이 되었다. 1487년 딸이 세자인 연산군의 빈(嬪)으로 뽑히자 그를 계기로 좌참찬, 1488년 한성부판윤, 1489년 예조판서, 1491년 사소대장(四所大將)을 겸대, 이어 이조판서가 되었다가 1492년 병으로 사직하였다.
                1494년 우의정, 1495(연산군 1) 좌의정·영의정, 1497년 3월 거창부원군(居昌府院君)에 봉해졌다. 1495년 4월∼1499년 2월에 걸쳐 영춘추관사(領春秋館事)로서 『성종실록』의 편찬을 주관하였다. 세종의 4남인 임영대군(臨瀛大君) 이구(李璆)의 딸과의 사이에서 신수근(愼守勤)·신수겸(愼守謙)·신수영(愼守英)을 두었다. 시호는 장성(章成)이다.
                """;



        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물에 대한 정보를 제공하는 가이드야, 정보가 주어지면, 그 인물에 대한 정보를 (년도) : 한 일 의 형식으로 잘라서 알려줘"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "내가 올린 정보는 신승선이라는 사람의 정보야 잘 요약해서 알려줘."));

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }

        System.out.println();
        CompletionsUsage usage = chatCompletions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, "
                        + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
    }
}
