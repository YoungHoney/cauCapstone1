package cap.backend.back.domain.gptresults;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Silok;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lifesummary {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;


    @Column(name="lifesummarycontents")
    private String contents;

    @OneToMany(mappedBy = "lifesummary")
    private List<Silok> siloks=new ArrayList<>();




}
