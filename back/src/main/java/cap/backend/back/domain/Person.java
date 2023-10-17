package cap.backend.back.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Person {

    @Id @GeneratedValue
    @Column(name="person_id")
    private Long id;

    @ManyToOne
    private Clan clan;

    @Column(length=10)
    private String name;

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
