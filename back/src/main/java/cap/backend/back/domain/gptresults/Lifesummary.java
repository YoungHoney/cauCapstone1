package cap.backend.back.domain.gptresults;


import cap.backend.back.domain.Krpedia;
import jakarta.persistence.*;

@Entity
public class Lifesummary {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;


    @Column(name="lifesummarycontents")
    private String contents;






}
