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

    String azureOpenaiKey = "81ffb8af272b4a468f9eb8a7a3b5ae21";
    String endpoint = "https://gpt4canadaletsgo.openai.azure.com/";
    String deploymentOrModelId = "MainModel_1";

    OpenAIClient client = new OpenAIClientBuilder()
            .endpoint(endpoint)
            .credential(new AzureKeyCredential(azureOpenaiKey))
            .buildClient();


    public void getGovsequence(String INFO) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물에 대한 정보를 제공하는 가이드야, 몇가지정보가 주어지면, 그 인물에 대한 정보를 (년도):(해당년도에인물이 오른 대표적 관직 하나) 의 형식으로 알려줘 :의 오른쪽에 오는 값은 반드시 단일 관직명이어야 해"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


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


    public String getLifesummary(String INFO,String ancestorname) {
        String result="";

        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 생애를 요약하는 프로그램이야, 인물기록을 입력해줄게, 될수있으면 단점보다는 장점위주로, 긍정적인 면을 중심으로 요약해봐"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
            result=message.getContent();
        }

        System.out.println();
        CompletionsUsage usage = chatCompletions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, "
                        + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

        return result;
    }

    public void getPrivateHistory(String INFO, String ancestorname) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 생애를 연도별로 요약하는 프로그램이야, 정보를 줄게"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "인물정보를 입력받고 <년도>:<인물이 한 일>을 쭉 나열해봐"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"결과 예시를 줄게, 만약 곽간이라는 인물에 대한 기록이라면, 입력: 1546년(명종 1) 증광문과에 병과로 급제하였으며, 1550년 형조좌랑에 임명되었다. 1552년 대동찰방 겸 수은어사(大同察訪兼搜銀御史)로 있을 때 중국에 사신으로 갔다가 돌아오는 심통원(沈通源)의 짐이 너무 많은 것을 보고 모두 뒤져서 불살라버렸다.\n" +
                "\n" +
                "이 사실이 관찰사를 통하여 조정에 보고되어 심통원이 파직당하자, 그의 보복이 두려워 미친 사람 행색으로 가장하고 소를 올리는 길로 서울을 떠났다. 그 뒤 10여 년 동안 피신하여 살았으며, 1566년 다시 성균관전적에 제수되었다.\n" +
                "\n" +
                "당시 문정왕후(文定王后)가 불교의 부흥을 꾀하자 이에 반대하는 상소를 하였다가 언관의 자리에서 밀려났다. 그러나 그 뒤 공조·형조·예조의 좌랑과 정랑·통례원통례·성균관사성·장악원판사·사제감·종부시정·영천군수·공주목사·강릉부사 등을 역임하였다.\n" +
                "\n" +
                "임진왜란이 일어나자 서사원(徐思遠)과 함께 초유사(招諭使) 김성일(金誠一)을 찾아가 싸우다가 김성일이 죽고 진영이 와해되어 돌아오던 중 죽었다. 저서로는 『죽재문집(竹齋文集)』 2권 1책이 있다. 월암사(月巖祠)에 봉향되었다"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"너의 답변 : 1546년:증광문과에 병과로 급제,1550년:형조좌랑에 임명됨,1552년:대동찰방 겸 수은어사(大同察訪兼搜銀御史)로 있을 때 중국에 사신으로 갔다가 돌아오는 심통원(沈通源)의 짐이 너무 많은 것을 보고 모두 뒤져서 불살라버렸다,1566년:성균관전적에제수되었다,"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이제 "+ancestorname+"에 관한 정보를 줄게"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


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

    public String getMBTI(String INFO, String ancestorname) {
        String result="";
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 MBTI가 어떤지 예측하는 프로그램이야, 비록 주관적일수 있지만 재미요소라서 상관없어 일단 예시를 줄게"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"결과 예시를 줄게, 입력 : 곽은이라는 사람이고, 기록을 줄게 조선 전기에, 담양부사, 승지 등을 역임한 문신.\n" +
                "접기/펼치기\n" +
                "개설\n" +
                "본관은 선산(善山). 자는 안부(岸夫). 곽윤성(郭允誠)의 증손으로, 할아버지는 곽유원(郭綏元)이고, 아버지는 생원 곽기(郭琦)이다.\n" +
                "\n" +
                "접기/펼치기\n" +
                "생애 및 활동사항\n" +
                "1472년(성종 3) 춘장문과(春場文科)에 갑과로 급제하여 승문원교검(承文院校檢)으로 등용되었으며, 그해 정조사(正朝使)의 서장관(書狀官)으로 명나라에 다녀왔다. 1481년에 지평이 된 뒤 헌납·전적·장령을 거쳐 1484년에는 전설사수(典設司守)가 되었다.\n" +
                "\n" +
                "그뒤 왕의 특명으로 담양부사로 나가 부역을 경감하는 등 선정을 베풀었다. 조정에서 치적을 높이 평가하여 승지로 승진시켰으나 부임 도중에 죽었다. 곽은이 죽자 부민들은 크게 비통해하였으며 오래도록 기신제(忌辰祭)를 지냈다.\n" +
                "\n" +
                "절의에 바르기로 유명한 남효온(南孝溫)도 그 죽음을 슬퍼하는 글을 지어 후세에 남겼고, 이이(李珥)도 곽은의 자손을 현인의 후예라 하여 등용할 것을 주청하였다. "));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"결과예시야, 답변 : 곽은의 기록을 통해 몇 가지 성격적 특성을 추측해 볼 수 있습니다:\n" +
                "\n" +
                "그는 공직에서 선정을 베푼 것으로 평가받았으며, 사후에도 부민들이 그를 오랫동안 기렸다고 합니다. 이는 그가 공동체와 그 구성원들에 대한 배려가 깊었으며, 그로 인해 사람들에게 존경받았음을 나타냅니다.\n" +
                "그는 선산 곽씨 집안에서 출생하여 당대의 사회적 기대와 전통적 가치를 수호하는 역할을 했습니다. 이는 그가 전통을 중시하고 체계 및 구조 내에서 안정을 추구했을 가능성을 나타냅니다.\n" +
                "남효온과 이이와 같은 당대 인물들이 그의 죽음을 애도하고 그의 후손을 기리는 것을 주장했다는 점은, 그가 도덕적으로 존경받는 인물이었음을 시사합니다.\n" +
                "이러한 특성을 볼 때, 곽은은 MBTI에서 I(Introverted, 내향적), S(Sensing, 감각적), F(Feeling, 감정적), J(Judging, 판단적) 유형에 속할 가능성이 있습니다. 예를 들어 ISFJ 유형은 '수호자'로 불리며, 전통을 중요시하고 타인에 대한 책임감이 강하며, 안정적인 환경을 추구하는 특성을 가지고 있습니다. 이러한 유형은 곽은이 보여준 행적과 일치하는 경향이 있습니다."));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
            result=message.getContent();

        }

        System.out.println();
        CompletionsUsage usage = chatCompletions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, "
                        + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
        return result;
    }


}
