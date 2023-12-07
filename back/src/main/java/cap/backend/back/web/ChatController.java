package cap.backend.back.web;

import cap.backend.back.domain.dto.MessageDTO;
import cap.backend.back.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatbotService chatbotService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    //private final RealService realService;


    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDTO messageDto) {
        try {// 사용자가 보낸 메시지를 처리하고 대화 응답을 생성
            chatbotService.generateChatbotResponse(messageDto);
        } catch (Exception e){
            simpMessagingTemplate.convertAndSend("/topic/messages", new MessageDTO("Error occured while " +
                    "fetching GPT Response. " + "Please try again"));
        }
    }

    @MessageMapping("/init")
    public void initChatbotService(){
        try{
            chatbotService.initChatbot();
        } catch(Exception e){
            simpMessagingTemplate.convertAndSend("/topic/messages", new MessageDTO("Error occured while " +
                    "initializing Chatbot " + "Please try again"));
        }
    }
}
