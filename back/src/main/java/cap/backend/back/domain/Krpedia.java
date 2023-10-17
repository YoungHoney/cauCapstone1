package cap.backend.back.domain;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(length=2)
    private String clanHangul;
    @Column(length=2)
    private String surnameHanja;
    @Column(length=2)
    private String surnameHangul;

    @Lob
    private byte[] personpicture;

    private String definition; //정의
    private String description; //개설

    private String maintext; //생애및활동사항




}
