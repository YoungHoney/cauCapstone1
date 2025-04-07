package cap.backend.back.api;

import cap.backend.back.domain.dto.ChatHistoriesDTO;
import cap.backend.back.domain.dto.MessageDTO;
import cap.backend.back.service.RealService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Component("ollamaapi")
@RequiredArgsConstructor
public class OllamaApi implements AiApi {

    @Value("${ollama.model}")
    private String model;

    private final WebClient webClient = WebClient.create("http://localhost:11434");
    private final ChatHistoriesDTO chatHistories = new ChatHistoriesDTO(3);
    private final RealService realService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public String getGovsequence(String info) {
        return callOllamaAPI(
                "너는 조선시대 인물에 대한 정보를 제공하는 가이드야. 몇 가지 정보가 주어지면, (년도):(관직) 형식으로 알려줘. 관직명은 반드시 단일 관직이어야 해.",
                "예시: 1582년 사마시에 급제하여 진사가 되고, 1595년 별시문과에 병과로 급제하여 승문원정자가 되었다.",
                "1582년:진사, 1595년:승문원정자, 1623년:동지중추부사, 1624년:가의대부",
                info
        ).block();
    }

    @Override
    public String getLifesummary(String info, String ancestorName) {
        return callOllamaAPI(
                "너는 조선시대 인물 '" + ancestorName + "' 에 대한 기록을 입력받아 생애를 요약하는 프로그램이야. 단점보다는 장점 위주로 요약해.",
                info
        ).block();
    }

    @Override
    public String getPrivateHistory(String info, String ancestorName) {
        return callOllamaAPI(
                "너는 조선시대 인물 '" + ancestorName + "' 에 대한 기록을 입력받아 연도별로 요약하는 프로그램이야.",
                "(년도):(한 일) 형식으로 정리해줘.",
                info
        ).block();
    }

    @Override
    public String getMBTI(String info, String ancestorName) {
        return callOllamaAPI(
                "너는 조선시대 인물 '" + ancestorName + "' 의 기록을 입력받아 MBTI를 예측하는 프로그램이야.",
                "결과는 [MBTI] 형식으로 출력해줘.",
                info
        ).block();
    }

    @Override
    public void clearChatHistories() {
        chatHistories.clearHistory();
    }

    @Override
    public void getReply(MessageDTO messageDto) {
        AtomicReference<Long> messageId = new AtomicReference<>(1L);
        List<String> responses = new ArrayList<>();

        Long ancestorId = messageDto.getAncestorId();
        String ancestorName = realService.findOne(ancestorId).getName();
        String ancestorInfo = realService.getGPTfood(ancestorId);

        String systemPrompt = "너는 조선시대 인물 '" + ancestorName + "' 이 되어 후손들에게 조언을 해주는 프로그램이야. 말투는 조상이 먼 후손에게 이야기하는 말투로 해줘.";
        String chatHistory = chatHistories.historiesToString();
        String userPrompt = messageDto.getMessage();

        String response = callOllamaAPI(systemPrompt, ancestorInfo, chatHistory, userPrompt).block();

        // 전송
        simpMessagingTemplate.convertAndSend("/topic/messages",
                new MessageDTO(response, messageId.getAndSet(messageId.get() + 1)));
        simpMessagingTemplate.convertAndSend("/topic/messageEnd",
                new MessageDTO("response ended"));

        chatHistories.add(userPrompt, response);
    }

    private Mono<String> callOllamaAPI(String... messages) {
        List<Map<String, String>> chatMessages = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            chatMessages.add(Map.of(
                    "role", i == messages.length - 1 ? "user" : "system",
                    "content", messages[i]
            ));
        }

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", chatMessages,
                "stream", false
        );

        return webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"));
    }
}
