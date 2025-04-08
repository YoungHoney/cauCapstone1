package cap.backend.back.api;

import cap.backend.back.domain.dto.ChatHistoriesDTO;
import cap.backend.back.domain.dto.MessageDTO;
import cap.backend.back.service.RealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component("geminiapi")
@RequiredArgsConstructor
public class GeminiApi implements AiApi {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com/v1beta/models");

    private final ChatHistoriesDTO chatHistories = new ChatHistoriesDTO(3);
    private final RealService realService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public String getGovsequence(String info) {
        String prompt = """
                너는 조선시대 인물 정보를 정리하는 도우미야.
                아래 텍스트에서 (년도):(해당년도 대표 관직 하나) 형식으로 요약해줘.
                관직명은 반드시 하나만 써야 해.

                내용:
                """ + info;

        System.out.println("getGovsequence의 인포 : "+info+"  |이상 종료");

        return callGeminiApi(prompt);
    }

    @Override
    public String getLifesummary(String info, String ancestorName) {
        String prompt = "조선 인물 '" + ancestorName + "'의 기록이 주어질 때 생애를 장점 위주로 요약해줘:\n\n" + info;
        return callGeminiApi(prompt);
    }

    @Override
    public String getPrivateHistory(String info, String ancestorName) {
        String prompt = "조선 인물 '" + ancestorName + "'의 기록이야. 연도별로 (년도):(해당 인물이 한 일) 형식으로 나열해줘:\n\n" + info;
        return callGeminiApi(prompt);
    }

    @Override
    public String getMBTI(String info, String ancestorName) {
        String prompt = "다음은 조선시대 인물 '" + ancestorName + "'의 생애 기록이야. 성격을 분석해서 MBTI를 [ISTJ] 형식으로 추정해줘:\n\n" + info;
        return callGeminiApi(prompt);
    }

    @Override
    public void getReply(MessageDTO messageDto) {
        AtomicReference<Long> messageId = new AtomicReference<>(1L);
        Long ancestorId = messageDto.getAncestorId();
        String ancestorName = realService.findOne(ancestorId).getName();
        String ancestorInfo = realService.getGPTfood(ancestorId);
        String chatHistory = chatHistories.historiesToString();

        String prompt = """
                너는 조선 인물 '%s'이 되어 후손의 질문에 조언을 해줘.
                말투는 옛 조상스럽게 해줘.
                
                [인물 정보]
                %s

                [지금까지의 대화]
                %s

                [질문]
                %s
                """.formatted(ancestorName, ancestorInfo, chatHistory, messageDto.getMessage());

        String reply = callGeminiApi(prompt);

        simpMessagingTemplate.convertAndSend("/topic/messages",
                new MessageDTO(reply, messageId.getAndSet(messageId.get() + 1)));
        simpMessagingTemplate.convertAndSend("/topic/messageEnd",
                new MessageDTO("response ended"));

        chatHistories.add(messageDto.getMessage(), reply);
    }

    @Override
    public void clearChatHistories() {
        chatHistories.clearHistory();
    }

    private String callGeminiApi(String prompt) {
        Map<String, Object> requestBody = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

        return webClient.post()
                .uri("/gemini-1.5-pro:generateContent?key=" + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                    if (candidates != null && !candidates.isEmpty()) {
                        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                        return parts.get(0).get("text");
                    }
                    return "[Gemini 응답 없음]";
                })
                .block();
    }
}
