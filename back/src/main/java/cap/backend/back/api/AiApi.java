package cap.backend.back.api;

import cap.backend.back.domain.dto.MessageDTO;

public interface AiApi {
    String getGovsequence(String info);
    String getLifesummary(String info, String ancestorName);
    String getPrivateHistory(String info, String ancestorName);
    String getMBTI(String info, String ancestorName);
    void getReply(MessageDTO messageDto);
    void clearChatHistories();
}
