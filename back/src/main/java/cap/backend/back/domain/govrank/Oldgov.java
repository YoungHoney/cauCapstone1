package cap.backend.back.domain.govrank;


import jakarta.persistence.*;

@Entity
public class Oldgov {
    @Id
    @Column(name="oldname")
    private String name;

    @Column(name="oldrank")
    private String rank;



    private boolean iswarrior;







}
