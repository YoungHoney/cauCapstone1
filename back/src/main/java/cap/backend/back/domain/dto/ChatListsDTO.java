package cap.backend.back.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter@Setter
public class ChatListsDTO {
    private String UserChat;
    private String GPTChat;

    public ChatListsDTO() {
        UserChat = "";
        GPTChat = "";
    }
    public String chatListsToString() {
        if(!(Objects.equals(UserChat, "")) && !(Objects.equals(GPTChat, ""))) {
            return "유저의 질문:\n" +
                    UserChat + "\n" +
                    "그에 대한 당신의 답변:\n" +
                    GPTChat + "\n";
        }
        else return null;
    }

    public void clear() {
        UserChat = "";
        GPTChat = "";
    }
}
