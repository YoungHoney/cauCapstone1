package cap.backend.back.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class OllamaService {

    private final WebClient webClient;

    public OllamaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
    }
/*
    public Mono<String> chatWithOllama(String model, String prompt) { //basic model : deepseek-r1:8b
        return webClient.post()
                .uri("/api/generate")
                .bodyValue(Map.of(
                        "model", "deepseek-r1:8b",
                        "prompt", prompt,
                        "stream", false
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"));
    }*/


    public Mono<String> chatWithOllama(String prompt) { //basic model : deepseek-r1:8b
        return webClient.post()
                .uri("/api/generate")
                .bodyValue(Map.of(
                        "model", "deepseek-r1:8b",
                        "prompt", prompt,
                        "stream", false
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"));
    }
}
