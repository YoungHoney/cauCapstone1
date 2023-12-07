package cap.backend.back.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MessageDTO {

    private String message;
    private Long ancestorId;
    private Long messageId;

    public MessageDTO(String message) {
        this.message=message;
    }
/*
    public MessageDTO(String message, Long ancestorId) {
        this.message=message;
        this.ancestorId = ancestorId;
    }

 */
    public MessageDTO(String message, Long messageId){
        this.message=message;
        this.messageId=messageId;
    }

}
