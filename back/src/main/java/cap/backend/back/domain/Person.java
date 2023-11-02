package cap.backend.back.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Person {

    @Id @GeneratedValue
    @Column(name="person_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "clanHangul"),
            @JoinColumn(name = "surnameHangul"),
            @JoinColumn(name = "surnameHanja")})
    private Clan clan; //본관과 양방향 다대일



    @OneToOne(mappedBy="person",fetch=FetchType.LAZY)
    @JsonIgnore //api 응답에 불필요한 정보
    private Krpedia krpedia; //사전과 양방향 일대일

    @Column(length=10)
    private String name;


    private String personpicture;

    private Integer birthyear;

    private Integer deathyear;


    //통무지정매 능력치 1~100 제한은 db단에서?
    private int tong;

    private int mu;

    private int ji;

    private int jung;

    private int mae;



    public Person() {}



}
