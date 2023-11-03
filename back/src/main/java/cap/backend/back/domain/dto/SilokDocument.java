package cap.backend.back.domain.dto;

import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class SilokDocument {
    private String dci;
    private String publicationYear;
    private String articleName;
    private String content;
    public SilokDocument(String dci, String publicationYear, String articleName){
        this.dci = dci;
        this.publicationYear = publicationYear;
        this.articleName = articleName;
    }

}