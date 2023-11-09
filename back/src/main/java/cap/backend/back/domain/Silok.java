package cap.backend.back.domain;

import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Silok {
    @Id @GeneratedValue
    @Column(name="silok_id")
    private Long id;

    @Column(name="person_id")
    private Long p_id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id",insertable = false,updatable = false)
    private Govsequence govsequence;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id",insertable = false,updatable = false)
    private Lifesummary lifesummary;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id",insertable = false,updatable = false)
    private Mbti mbti;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id",insertable = false,updatable = false)
    private Privatehistory privatehistory;

    @Lob
    @Column(name="silokcontents",columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contents;



    private Integer eventyear;







}
