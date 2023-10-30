package cap.backend.back.domain.gptresults;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import cap.backend.back.domain.govrank.Oldgov;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class Govsequence {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="oldname")
    private Oldgov oldgov;



    @OneToMany(mappedBy = "govsequence")
    private List<Silok> siloks=new ArrayList<>();

    private Integer sequnce_num;





}
