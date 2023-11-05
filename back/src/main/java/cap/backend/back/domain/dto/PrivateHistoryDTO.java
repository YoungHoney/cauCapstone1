package cap.backend.back.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PrivateHistoryDTO { //일단 만들어는 놨는데 데모에선 안쓰임.

    private int count;

    private List<Integer> eventyear;
    private List<String> contents;


}
