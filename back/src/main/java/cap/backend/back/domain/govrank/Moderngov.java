package cap.backend.back.domain.govrank;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Moderngov {
    @Id
    @Column(name="modernname")
    private String name;

    @Column(name="modernrank")
    private String rank;

    private boolean iswarrior;

    @Column(name="modernpersonname")
    private String personname;

    @Lob
    @Column(name="modernpersonpicture")
    private byte[] personpicture;




}
