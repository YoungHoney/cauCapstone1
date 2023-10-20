package cap.backend.back.domain.govrank;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Oldgov {
    @Id
    @Column(name="oldname")
    private String name;

    @Column(name="oldrank")
    private String rank;
    private boolean iswarrior;



    @OneToMany(mappedBy = "oldgov")
    private List<Govmatch> govmatches=new ArrayList<>();





}
