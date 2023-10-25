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
            @JoinColumn(name = "surnameHanja"),
            @JoinColumn(name = "surnameHangul")})
    private Clan clan; //본관과 양방향 다대일



    @OneToOne(mappedBy="person",fetch=FetchType.LAZY)

    @JsonIgnore
    private Krpedia krpedia; //사전과 양방향 일대일

    @Column(length=10)
    private String name;


    private String personpicture;

    private int birthyear;

    private int deathyear;


    //통무지정매 능력치 1~100 제한은 db단에서?
    private int tong;

    private int mu;

    private int ji;

    private int jung;

    private int mae;



    public Person() {}



}
