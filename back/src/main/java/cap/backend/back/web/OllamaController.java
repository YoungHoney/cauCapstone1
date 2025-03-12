package cap.backend.back.web;

import cap.backend.back.service.OllamaService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ochat")
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }
/*
    @GetMapping("/{model}")
    public Mono<String> chat(@PathVariable String model, @RequestParam String prompt) {
        return ollamaService.chatWithOllama(model, prompt);
    }*/


    @GetMapping
    public Mono<String> chat(@RequestParam String prompt) {
        return ollamaService.chatWithOllama(prompt);
    }
}
