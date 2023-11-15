package cap.backend.back.domain.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MessageDTO {

    private String message;

    public MessageDTO(String message) {
        this.message=message;
    }

}
