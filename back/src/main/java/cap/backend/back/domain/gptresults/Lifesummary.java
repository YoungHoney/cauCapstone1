package cap.backend.back.domain.gptresults;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Silok;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Lifesummary {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;


    @Lob
    @Column(name="lifesummarycontents",columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contents;

    @OneToMany(mappedBy = "lifesummary")
    private List<Silok> siloks=new ArrayList<>();




}
