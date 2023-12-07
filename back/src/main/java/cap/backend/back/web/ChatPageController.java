package cap.backend.back.web;

import cap.backend.back.service.RealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatPageController {

    private final RealService realService;

    @GetMapping("/api/ancestor/{id}/chat")
    public String showChatPage(@PathVariable Long id, Model model) {
        model.addAttribute("ancestorId",id);
        model.addAttribute("aname", realService.findOne(id).getName());

        return "chat"; // chat.html 템플릿을 렌더링
    }
}
