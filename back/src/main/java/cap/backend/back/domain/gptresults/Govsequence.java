package cap.backend.back.domain.gptresults;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import jakarta.persistence.*;


@Entity
public class Govsequence {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;

    @JoinColumn(referencedColumnName = "oldname")
    private String oldgovname;





}
