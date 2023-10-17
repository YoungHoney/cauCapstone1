package cap.backend.back.domain.govrank;

import cap.backend.back.domain.compositekey.GovmatchId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Govmatch {

    @EmbeddedId
    private GovmatchId govmatchId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="oldname",insertable = false,updatable = false)
    private Oldgov oldgov;

    @ManyToOne
    @JoinColumn(name="modernname",insertable = false,updatable = false)
    private Moderngov moderngov;


}
