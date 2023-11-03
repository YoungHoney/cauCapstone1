package cap.backend.back.domain;

import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Krpedia {

    @Id
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person; //인물과 양방향 일대일

    @Column(length=10)
    private String name;

    private int birthyear;

    private int deathyear;

    @Column
    private String clanHangul;
    @Column
    private String surnameHanja;
    @Column
    private String surnameHangul;

    @Lob
    private String personpicture;

    @Lob
    private String definition; //정의
    @Lob
    private String description; //개설
    @Lob
    private String maintext; //생애및활동사항


//각 gpt 결과들에대해 결과들만 사전내용을 알도록 함
//    @OneToOne(mappedBy = "krpedia",fetch=FetchType.LAZY)
//    private Lifesummary lifesummary; //활동요약과 양방향 일대일
//
    @OneToMany(mappedBy = "krpedia",fetch=FetchType.LAZY)
    private List<Govsequence> govsequence; //관직순서와 양방향 일대일
//
//    @OneToOne(mappedBy = "krpedia",fetch=FetchType.LAZY)
//    private Mbti mbti; //mbti식 요약과 양방향 일대일
//
    @OneToMany(mappedBy = "krpedia")
    private List<Privatehistory> privatehistory; //개인사건과 양방향 일대일




}
