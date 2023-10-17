package cap.backend.back.domain.govrank;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Govmatch {


    @Id
    @GeneratedValue
    @Column(name="Govmatch_id")
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="oldname")
    private Oldgov oldgov;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="modernname")
    private Moderngov moderngov;



}
