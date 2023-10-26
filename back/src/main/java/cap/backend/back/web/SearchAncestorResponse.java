package cap.backend.back.web;

import cap.backend.back.domain.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter@Setter
@Builder
public class SearchAncestorResponse {
    private Person ancestor;
    private String lifeSummary;
    private  Map<Integer, String> timeline;
    private Map<Integer, String> mainEvents;
    private Map<String, String> govSequence;
    private String personPicPath;
    private String imaginaryPicPath;
    private String mbti;
    private String mbtiContent;

}
