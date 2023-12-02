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

    private String birthyear;

    private String deathyear;

    @Column
    private String clanHangul;
    @Column
    private String surnameHanja;
    @Column
    private String surnameHangul;

    @Lob
    private String personpicture;

    @Lob
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String definition; //정의
    @Lob
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description; //개설
    @Lob
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String maintext; //생애및활동사항



    @OneToMany(mappedBy = "krpedia",fetch=FetchType.LAZY)
    private List<Govsequence> govsequence; //관직순서와 양방향 일대일

    @OneToMany(mappedBy = "krpedia")
    private List<Privatehistory> privatehistory; //개인사건과 양방향 일대일




}
