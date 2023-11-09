package cap.backend.back.web;

import cap.backend.back.domain.dto.MessageDto;
import cap.backend.back.service.ChatService;
import cap.backend.back.service.RealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final RealService realService;



    @GetMapping("/{id}/api/chat")
    public String showChatPage(@PathVariable Long id, Model model) {
        model.addAttribute("ancestorId",id);
        return "chat"; // chat.html 템플릿을 렌더링
    }




    @PostMapping("/{id}/api/chat")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto, @PathVariable Long id) {

        try {

            String reply = chatService.getReplyFromAzure(messageDto,realService.findOne(id).getName());
            return ResponseEntity.ok(new MessageDto(reply));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
