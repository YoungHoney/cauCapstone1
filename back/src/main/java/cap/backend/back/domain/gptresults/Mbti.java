package cap.backend.back.domain.gptresults;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Mbti {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;

    private String mbti;


    @Column(name="mbticontents")
    private String contents;


    @OneToMany(mappedBy = "mbti")
    @JoinColumn(name="mbti")
    private List<Silok> siloks=new ArrayList<>();
}
