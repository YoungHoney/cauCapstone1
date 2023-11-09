package cap.backend.back.domain.gptresults;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Mbti {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Krpedia krpedia;

    private String mbti;

    @Lob
    @Column(name="mbticontents",columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contents;


    @OneToMany(mappedBy = "mbti")
    private List<Silok> siloks=new ArrayList<>();
}
