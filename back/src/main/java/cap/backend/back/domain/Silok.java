package cap.backend.back.domain;

import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import jakarta.persistence.*;

@Entity
public class Silok {
    @Id
    @Column(name="silok_id")
    private Long id;


    @Column(name="person_id")
    private Long p_id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Govsequence govsequence;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Lifesummary lifesummary;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Mbti mbti;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Privatehistory privatehistory;










}
