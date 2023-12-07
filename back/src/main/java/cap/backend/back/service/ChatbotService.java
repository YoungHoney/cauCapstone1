package cap.backend.back.service;

import cap.backend.back.api.AzureApi;
import cap.backend.back.domain.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotService {
    private final AzureApi azureApi;
    public void generateChatbotResponse(MessageDTO userMessage){
        try {
            azureApi.getReply(userMessage);
        } catch (Exception e) {
            e.printStackTrace();;
            throw e;
        }
    }

    public void initChatbot(){
        try{
            azureApi.clearChatHistories();
        }catch (Exception e){
            System.out.println("failed initializing Chatbot!\n");
            throw e;
        }
    }
}
