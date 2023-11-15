package cap.backend.back.domain.dto;

import cap.backend.back.domain.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter@Setter
public class SearchAncestorResponseDTO {
    private Person ancestor;
    private String definition;
    private String lifeSummary;
    private  Map<Integer, String> timeline;
    private Map<Integer, String> mainEvents;
    private Map<Integer, String> govSequence;
    private String personPicPath;
    private String imaginaryPicPath;

    private String[] modernPersonandGov;
    private String mbti;
    private String mbtiContent;

    // Constructor with parameters for non-default values
    public SearchAncestorResponseDTO(
            Person ancestor, String definition, String lifeSummary,
            Map<Integer, String> timeline, Map<Integer, String> mainEvents, Map<Integer, String> govSequence,
            String personPicPath, String imaginaryPicPath, String[] modernPersonandGov,
            String mbti, String mbtiContent) {
        this.ancestor = ancestor;
        this.definition = definition;
        this.lifeSummary = lifeSummary;
        this.timeline = timeline;
        this.mainEvents = mainEvents;
        this.govSequence = govSequence;
        this.personPicPath = personPicPath;
        this.imaginaryPicPath = imaginaryPicPath;
        this.modernPersonandGov = modernPersonandGov;
        this.mbti = mbti;
        this.mbtiContent = mbtiContent;
    }

}
