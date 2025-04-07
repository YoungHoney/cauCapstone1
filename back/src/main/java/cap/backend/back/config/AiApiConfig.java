package cap.backend.back.config;

import cap.backend.back.api.AiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AiApiConfig {

    private final Map<String, AiApi> aiApiMap;
    private final String provider;

    public AiApiConfig(Map<String, AiApi> aiApiMap, @Value("${ai.provider}") String provider) {
        this.aiApiMap = aiApiMap;
        this.provider = provider;
    }

    @Bean
    public AiApi aiApi() {
        AiApi selected = aiApiMap.get(provider);
        if (selected == null) {
            throw new IllegalArgumentException("지원하지 않는 AI 제공자입니다: " + provider);
        }
        return selected;
    }
}