package cap.backend.back.api;

import cap.backend.back.domain.dto.ChatHistoriesDTO;
import cap.backend.back.domain.dto.MessageDTO;
import cap.backend.back.service.RealService;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.IterableStream;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AzureApi {

    @Value("${azure.openaikey}")
    String azureOpenaiKey;
    @Value("${azure.endpoint}")
    String endpoint;
    @Value("${azure.model}")
    String deploymentOrModelId;
    //MokModel gpt3.5
    //MainModel_1 gpt4

    OpenAIClient client;
    private final ChatHistoriesDTO chatHistories = new ChatHistoriesDTO(3);
    private final RealService realService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void init() {
        this.client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(azureOpenaiKey))
                .buildClient();
    }


    public String getGovsequence(String INFO) {
        String result="we";
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물에 대한 정보를 제공하는 가이드야, 몇가지정보가 주어지면, 그 인물에 대한 정보를 (년도):(해당년도에인물이 오른 대표적 관직 하나) 의 형식으로 알려줘 :의 오른쪽에 오는 값은 반드시 단일 관직명이어야 해"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "예시를 줄게, 1582년(선조 15) 사마시에 급제하여 진사가 되고, 1595년 별시문과에 병과로 급제하여 승문원정자가 되었다. 할아버지의 공으로 성균관전적(成均館典籍)으로 특진하였으며, 내외의 관직을 역임하여 우승지에 이르렀다. 1613년(광해군 5) 계축옥사가 일어났을 때 첩의 남동생인 서양갑(徐羊甲)에게 연루되어 파직당하였다.\n" +
                "\n" +
                "그 뒤 1620년 무관직에 여러 번 임명되었으나, 병을 칭탁하고 당시 대북세력이 장악하고 있던 조정에 나가지 않았다. 1623년 인조반정 후 동지중추부사·오위도총부부총관, 한성부의 좌윤·우윤 등을 역임하였다.\n" +
                "\n" +
                "1624년(인조 2) 이괄(李适)의 난이 일어났을 때에는 인조를 공주에 호종하였으며, 그 뒤 가의대부(嘉義大夫)에 올랐다. 1627년 정묘호란 때에는 왕을 강화로 호종(護從: 호위하여 따름.)하였으며, 적과의 강화를 강력히 배척하는 소를 올렸다.\n" +
                "\n" +
                "1632·1635년에는 예조참판으로 재직하면서 인목대비(仁穆大妃)와 인열왕후(仁烈王后)의 국장업무에 참여하였다. 1636년 병자호란 때에는 왕을 남한산성에 호종하였으며, 다음해 서울에 돌아와 지중추부사에 임명되었다. 저서로는 『계음만필(溪陰漫筆)』·『도재수필』·『도재집』 등이 있다. 시호는 정민(靖敏)이다."));

        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT,"1582년:진사,1595년:승문원정자,1623년:동지중추부사,1624년:가의대부,1632년:예조참판,1635년:예조참판,1637년:지중추부사"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물에 대한 정보를 제공하는 가이드야, 몇가지정보가 주어지면, 그 인물에 대한 정보를 (년도):(해당년도에인물이 오른 대표적 관직 하나) 의 형식으로 알려줘 :의 오른쪽에 오는 값은 반드시 단일 관직명이어야 해"));

        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));



        ChatCompletionsOptions options=new ChatCompletionsOptions(chatMessages);
        options.setTemperature(0.0);


        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, options);

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


    public String getLifesummary(String INFO,String ancestorname) {
        String result="";

        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 생애를 요약하는 프로그램이야, 인물기록을 입력해줄게, 될수있으면 단점보다는 장점위주로, 긍정적인 면을 중심으로 요약해봐"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


        ChatCompletionsOptions options=new ChatCompletionsOptions(chatMessages);
        options.setTemperature(0.0);

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, options);

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

    public String getPrivateHistory(String INFO, String ancestorname) {
        String result="as";
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 생애를 연도별로 요약하는 프로그램이야, 정보를 줄게"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "인물정보를 입력받고 <년도>:<인물이 한 일>을 쭉 나열하고 $로 각 정보를 구분해봐"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"결과 예시를 줄게, 만약 곽간이라는 인물에 대한 기록이라면, 입력: 1546년(명종 1) 증광문과에 병과로 급제하였으며, 1550년 형조좌랑에 임명되었다. 1552년 대동찰방 겸 수은어사(大同察訪兼搜銀御史)로 있을 때 중국에 사신으로 갔다가 돌아오는 심통원(沈通源)의 짐이 너무 많은 것을 보고 모두 뒤져서 불살라버렸다.\n" +
                "\n" +
                "이 사실이 관찰사를 통하여 조정에 보고되어 심통원이 파직당하자, 그의 보복이 두려워 미친 사람 행색으로 가장하고 소를 올리는 길로 서울을 떠났다. 그 뒤 10여 년 동안 피신하여 살았으며, 1566년 다시 성균관전적에 제수되었다.\n" +
                "\n" +
                "당시 문정왕후(文定王后)가 불교의 부흥을 꾀하자 이에 반대하는 상소를 하였다가 언관의 자리에서 밀려났다. 그러나 그 뒤 공조·형조·예조의 좌랑과 정랑·통례원통례·성균관사성·장악원판사·사제감·종부시정·영천군수·공주목사·강릉부사 등을 역임하였다.\n" +
                "\n" +
                "임진왜란이 일어나자 서사원(徐思遠)과 함께 초유사(招諭使) 김성일(金誠一)을 찾아가 싸우다가 김성일이 죽고 진영이 와해되어 돌아오던 중 죽었다. 저서로는 『죽재문집(竹齋文集)』 2권 1책이 있다. 월암사(月巖祠)에 봉향되었다"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"1546년:증광문과에 병과로 급제$1550년:형조좌랑에 임명됨$1552년:대동찰방 겸 수은어사(大同察訪兼搜銀御史)로 있을 때 중국에 사신으로 갔다가 돌아오는 심통원(沈通源)의 짐이 너무 많은 것을 보고 모두 뒤져서 불살라버렸다$1566년:성균관전적에제수되었다"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이제 "+ancestorname+"에 관한 정보를 줄게"));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


        ChatCompletionsOptions options=new ChatCompletionsOptions(chatMessages);
        options.setTemperature(0.0);

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, options);


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

    public String getMBTI(String INFO, String ancestorname) {
        String result="";
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 "+ancestorname+"에 대한 기록들을 입력받아 인물의 MBTI가 어떤지 예측하는 프로그램이야, 답장할때 중괄호 안에 MBTI를 입력하면 되, 예를들어, 예측결과가 ISTJ라면 [ISTJ]라고 하면 되, 비록 주관적일수 있지만 재미요소라서 상관없어 일단 예시를 줄게"));
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
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM,"결과예시: [ISFJ] 곽은의 기록을 통해 몇 가지 성격적 특성을 추측해 볼 수 있습니다:\n" +
                "\n" +
                "그는 공직에서 선정을 베푼 것으로 평가받았으며, 사후에도 부민들이 그를 오랫동안 기렸다고 합니다. 이는 그가 공동체와 그 구성원들에 대한 배려가 깊었으며, 그로 인해 사람들에게 존경받았음을 나타냅니다.\n" +
                "그는 선산 곽씨 집안에서 출생하여 당대의 사회적 기대와 전통적 가치를 수호하는 역할을 했습니다. 이는 그가 전통을 중시하고 체계 및 구조 내에서 안정을 추구했을 가능성을 나타냅니다.\n" +
                "남효온과 이이와 같은 당대 인물들이 그의 죽음을 애도하고 그의 후손을 기리는 것을 주장했다는 점은, 그가 도덕적으로 존경받는 인물이었음을 시사합니다.\n" +
                "이러한 특성을 볼 때, 곽은은 MBTI에서 I(Introverted, 내향적), S(Sensing, 감각적), F(Feeling, 감정적), J(Judging, 판단적) 유형에 속할 가능성이 있습니다. 예를 들어 ISFJ 유형은 '수호자'로 불리며, 전통을 중요시하고 타인에 대한 책임감이 강하며, 안정적인 환경을 추구하는 특성을 가지고 있습니다. 이러한 유형은 곽은이 보여준 행적과 일치하는 경향이 있습니다."));
        chatMessages.add(new ChatMessage(ChatRole.USER, INFO));
        //   chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Yes, customer managed keys are supported by Azure OpenAI?"));


        ChatCompletionsOptions options=new ChatCompletionsOptions(chatMessages);
        options.setTemperature(0.0);

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, options);

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

    // ---------------------- Chatbot 관련 코드 -----------------------//
    public void clearChatHistories(){
        chatHistories.clearHistory();
    }

    public void getReply(MessageDTO messageDto) {
        AtomicReference<Long> messageId = new AtomicReference<>(1L);
        List<String> gptResponses = new ArrayList<>();
        List<ChatMessage> chatMessages = new ArrayList<>();
        Long ancestorId = messageDto.getAncestorId();
        System.out.println("조상의 id:" + ancestorId + "\n");
        String ancestorName = realService.findOne(ancestorId).getName();

        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "너는 조선시대 인물인 '" + ancestorName + "' 이 되어 후손들에게 조언을 해주는 프로그램이야, 말투는 조상이 먼 후손에게 이야기하는 말투로 해줘"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, ancestorName+"에 대한 정보를 줄게:\n"));
        //SetDemoInfo(chatMessages,ancestorName);

        //동적으로 정보 받아와서 넘겨주기
        String ancestorInfo = realService.getGPTfood(ancestorId);
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, ancestorInfo + "\n"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "여기까지가 '" + ancestorName));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "' 에 대한 정보야. 다시 말하지만 이 정보를 가지고 '" + ancestorName));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "'이 되어 후손들에게 조언을 해주고, 묻는 질문에 대답을 해줘.\n"));

        
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "그리고 이것은 지금까지 우리가 나눈 대화의 일부야:\n"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, chatHistories.historiesToString()));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이제부터 진짜 대화를 시작할게.\n"));
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, ". 대답은 간결하게 해 " + ancestorName + "야\n"));
        chatMessages.add(new ChatMessage(ChatRole.USER, messageDto.getMessage()));

        // 프롬포트 출력
        System.out.println(chatMessages.stream()
                .map(ChatMessage::getContent)
                .collect(Collectors.joining()));

        // temperature 0으로 설정
        ChatCompletionsOptions options=new ChatCompletionsOptions(chatMessages);
        options.setTemperature(0.0);

        IterableStream<ChatCompletions> chatCompletionsStream = client.getChatCompletionsStream(
                deploymentOrModelId, options);
        chatCompletionsStream
                .stream()
                .skip(1)
                .forEach(chatCompletions -> {
                    ChatMessage delta = chatCompletions.getChoices().get(0).getDelta();
                    if (delta.getRole() != null) {
                        System.out.println("Role = " + delta.getRole());
                    }
                    if (delta.getContent() != null) {
                        String responseContent = delta.getContent();
                        System.out.print(responseContent);
                        gptResponses.add(responseContent);
                        simpMessagingTemplate.convertAndSend("/topic/messages", new MessageDTO(responseContent,
                                messageId.getAndSet(messageId.get() + 1)));
                    }
                });
        // 응답(스트림)이 끝난 것을 프론트에 알려 새로운 응답 박스가 생기게 하는 역할
        simpMessagingTemplate.convertAndSend("/topic/messageEnd", new MessageDTO("response ended"));
        //chatHistory 에 유저의 질문과 그에 대한 답변을 집어 넣는다
        chatHistories.add(messageDto.getMessage(), String.join("", gptResponses));
    }

    private void SetDemoInfo(List<ChatMessage> chatMessages,String ancestorname) {

        switch (ancestorname) {
            case "박세채(朴世采)" -> {
                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "박세채는 반남 박씨 가문에서 출생하여, 조선 시대의 교육 기관인 성균관에서 학문을 시작하였습니다. 그는 이이(李珥)의 학문에 깊은 존경심을 가지고 있었으며, 이는 그가 훗날 학자로서 나아가는 데 중요한 밑거름이 되었습니다. 젊은 시절에는 유명한 학자들의 문하에서 수학하여 지식과 식견을 넓혔고, 이후 학자이자 정치인으로서의 삶을 시작하였습니다.\n" +
                        "\n" +
                        "1659년에는 국가의 중요 관직에 천거되어 익위사세마(翊衛司洗馬)가 되었으며, 남인과 서인으로 나뉜 당시 정치 상황에서 서인 측의 이론가로 활약하며 남인에 맞서 주장을 펼쳤습니다. 특히 복제사의(服制私議)를 통해 남인의 주장을 체계적으로 반박하면서 자신의 지식과 논리력을 드러냈습니다.\n" +
                        "\n" +
                        "그의 학문적 업적도 빼어났습니다. 유배 생활 중에도 학문에 전념하여 다수의 저술을 남겼습니다. 이는 조선 시대 성리학을 비롯하여 여러 학문 분야에 귀중한 자료로 평가받고 있습니다.\n" +
                        "\n" +
                        "정치적으로는 여러 차례 관직에서 삭탈당하고 유배를 겪는 등의 고난을 겪었지만, 이는 그가 자신의 신념을 굽히지 않았다는 증거로 볼 수 있습니다. 그리고 정계에 복귀했을 때에는 대사헌, 이조판서 등 중요한 직책을 역임하며 조선의 정치에 크게 기여하였습니다.\n" +
                        "\n" +
                        "박세채는 소론의 영도자로서 논리적이고 정의로운 입장을 견지하며, 이이와 성혼에 대한 문묘종사 문제의 해결에 기여했을 뿐만 아니라, 대동법과 같은 중요한 정책의 실시를 주장하여 사회 및 경제 개혁에도 큰 영향을 미쳤습니다.\n" +
                        "\n" +
                        "마지막으로, 박세채는 당시 격동적인 국내외 상황 속에서도 학문적으로나 정치적으로 중요한 발자취를 남긴 인물로서, 후세에 큰 존경을 받고 있는 인물입니다. 그의 삶은 신념과 학문, 그리고 불굴의 정신으로 가득 찬 조선 시대의 위대한 선비의 모습을 보여줍니다."));

                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이상이 " + ancestorname + "에 대한 정보입니다. 너는 위대한 학자인 박세채로서, 대학생인 사용자에게 학자로서의 신념과 마음가짐에 대해 가르쳐주고, 그들의 질문에 대해 위 정보를 바탕으로 대답하세요"));
            }
            case "김상익(金尙翼)" -> {
                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "김상익은 조선 후기의 우수한 문신으로, 국가의 핵심을 담당하는 다양한 역할을 수행했다. 병과로의 급제 이후 지평과 정언을 맡았고, 충청도어사를 비롯해 수찬, 교리, 헌납 등의 역임을 했다. 그는 이조좌랑을 지낸 후 세자시강원필선, 대사간 등에 오르며 그 능력을 인정받았고, 경기도 관찰사와 대사헌 등도 맡았다.\n" +
                        "\n" +
                        "사은부사로 외교적 목적으로 청나라에 다녀왔고, 그 후로도 지속적으로 중요한 직책을 맡았다. 1759년에 도승지, 1763년에 지경연사, 1765년에 강화유수 등을 역임했다. 그는 형제들과 함께 기로소에 들어가 봉조하가 되었고, 이는 그의 삶이 복문, 즉 행운의 문이라고 일컬어진 것을 의미한다.\n" +
                        "\n" +
                        "김상익은 헌납을 맡았을 때, 궁중의 부조리를 용감하게 고발해 왕의 비난을 받았지만, 그의 청렴함과 충심을 보여주는 계기가 되며 많은 존경을 받았다. 이것은 그가 진정한 국가 기능을 수행하고자 하는 신하라는 것을 보여주며, 그의 생애는 긍정적인 정치 활동과 우수한 공적을 달성하기 위한 노력으로 가득 차있다."));

                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이상이 김상익에 대한 정보입니다. 너는 청렴한 선비인 김상익이 되어, 사회 초년생인 사용자에게 사회 구성원으로서 청렴함에 대해 얘기하고 또 청나라를 다녀와서 선진문물을 보고와서 깨우쳤으므로, 사용자에게 나라 안에만 있지말고, 세계로 나아가라는 느낌으로 조언해줘"));

            }
            case "권응수(權應銖)" -> {
                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "권응수는 조선시대 뛰어난 무신과 의병장으로, 별시무과에 급제하여 여러 고을의 의병장을 이끌며 고향을 지키는데 주력하였습니다. 임진왜란이 일어났을 때, 권응수는 의병을 모집하여 활발히 활동하였고, 영천성을 화공으로 대승해 수복하는 등 여러 전과를 올렸습니다. \n" +
                        "\n" +
                        "그는 안동의 모은루에서 적을 대파하고, 밀양의 적을 격파하며 계속해서 전공을 세웠습니다. 그의 공로로 여러 차례에 걸쳐 경상도병마좌별장, 충청도방어사, 경상도방어사 등의 높은 지위를 밟았습니다. 그리고 그는 병마절도사 김응서와 함께 정유재란 때 달성까지 추격하며 적을 물리친 기록이 있습니다.\n" +
                        "\n" +
                        "권응수는 그 시대의 전략과 지혜를 이용하여 민중을 이끌며 전쟁을 치르는 동안, 그의 탁월한 지휘력과 용감함을 앞세워 수많은 승리를 이끌어냈으며, 그의 공로는 선무공신 2등으로 기록되었습니다. 그는 이와 같은 여러가지 공로로 인해 시호는 충의(忠毅)로 추증되었습니다."));

                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이상이 권응수에 대한 정보입니다. 너는  권응수가 되어, 개발자를 지망하여 운동을 잘 안하는 사용자에게 운동을 열심히 하라고 독려해주세요, 살짝 타박하는 목소리로 의자에 앉아있지만 말고 운동좀 하라고 해주세요"));

            }
            case "이산해(李山海)" -> {
                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "\t이산해는 조선 중기의 중요한 관리로서 홍문관 정자, 사헌부집의, 영의정 등을 역임했습니다. 그의 생애는 국가의 재정과 국내외의 외교 등 다양한 무대에서 일어났으며, 그의 행정 역량은 선조의 강력한 지배 아래에서 피어났습니다. \n" +
                        "\n" +
                        "그는 학문에 뛰어나서 어린 나이에 진사가 되었으며, 그 후 다양한 고위 직책을 역임하며 급속히 승진하였습니다. 그는 선조의 즉위년에 원접사의 종사관으로 명나라 조사를 맞이하였고, 이 조정랑, 의정부사인, 사헌부집의 등 다수의 고위 직책을 맡았습니다. \n" +
                        "\n" +
                        "그는 많은 정치적인 이벤트, 특히 북인과의 정치적인 대립 속에서 핵심 역할을 하였습니다. 그는 재차 영의정에 올라 서인들을 능가하였고, 그의 지도력과 행정 능력 덕분에 그는 조선 정부의 핵심 인물이 되었습니다.\n" +
                        "\n" +
                        "그러나 그는 그의 권력을 이용해 정치적인 적들을 탄압하는 등의 부정적인 행동도 일삼았습니다. 그는 아들 이경전을 이용해 정철을 유배시키고 서인 계파의 문신을 탄핵하였습니다.\n" +
                        "\n" +
                        "병사들에 대한 그의 지원과 도움은 그를 뛰어난 지도자로 인식하게 하였고, 그의 성공은 그의 잠재력과 열정을 입증하였습니다. 그의 공헌은 그를 국가의 중요한 인물로서 인정받게 하였지만, 그의 권력 악용은 그를 많은 비판에 노출시켰습니다.\n" +
                        "\n" +
                        "그는 또한 문학과 예술에서도 뛰어난 재능을 보였습니다. 그는 선조조 문장팔가 중 한 사람으로 불렸고, 대자와 산수묵도에 뛰어나 보여 주었습니다. 그의 저서인 \"아계집\"은 그의 학문적 성취를 입증하는 또 다른 증거였습니다.\n" +
                        "\n" +
                        " 이산해는 조선 중기의 주요한 정치적 인물로서 많은 역할을 하였습니다. 그는 재정, 국내외 정치, 그리고 문학과 예술에 이르기까지 그의 역량은 상당했습니다. 그러나 그의 권력 악용과 보수적인 태도는 그에 대한 많은 비판을 받았습니다. 그럼에도 불구하고, 그의 공헌은 그를 조선의 중요한 인물로 만들었으며, 그는 조선 역사에서 중요한 위치를 차지하고 있습니다."));

                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "다음은 이산해가 쓴 시 입니다. 사용자가 시를 읊어달라고 하면 이 시를 읊어주세요 제목: 모산(暮山) 내용:海天風定日沈霞(해천풍정일침하) 바람 그친 하늘, 해 지는 노을 \n" +
                        "蒲葦洲邊夕露多(포위주변석노다) 부들, 갈대 우거진 물가엔 이슬도 많아라\n" +
                        "瘦馬倒鞭沙路逈(수마도편사노형) 여윈 말에 채찍질하여도 길은 멀어\n" +
                        "夜深明月宿漁家(야심명월숙어가) 밤 깊고 달 밝아 어촌에서 묵어가려네"));
                chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "이상이 이산해에 대한 정보입니다. 사용자가 당신에게 사회적으로 성공을 하려면 어떻게 해야하는지 물어보면 정보를 바탕으로 답해주고, 시를 읊어달라고 하면 시를 읊고, 의미를 말해달라고 하면 임진왜란때 선조와 함께 피난가다가 쓴 시라고 해줘 "));

            }
        }





    }


}
