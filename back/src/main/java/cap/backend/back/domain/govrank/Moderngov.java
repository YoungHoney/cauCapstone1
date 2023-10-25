package cap.backend.back.domain.govrank;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Moderngov {
    @Id
    @Column(name="modernname")
    private String name;

    @Column(name="modernrank")
    private String rank;

    private boolean iswarrior;

    @Column(name="modernpersonname")
    private String personname;


    @Column(name="modernpersonpicture")
    private String personpicture;


    @OneToMany(mappedBy = "moderngov")
    private List<Govmatch> govmatches=new ArrayList<>();
}
