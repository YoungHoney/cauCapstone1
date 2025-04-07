package cap.backend.back.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${krpedia.api.key}")
    private String krpediaApiKey;

    public String getKrpediaApiKey() {
        return krpediaApiKey;
    }
}
