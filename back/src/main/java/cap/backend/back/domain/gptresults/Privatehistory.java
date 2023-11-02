package cap.backend.back.domain.gptresults;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class Privatehistory {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;


    private Integer eventyear;


    @OneToMany(mappedBy = "privatehistory")
    private List<Silok> siloks=new ArrayList<>();

    @Column(name="privatehistorycontents")
    private String contents;

}
