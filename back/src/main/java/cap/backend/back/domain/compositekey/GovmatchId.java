package cap.backend.back.domain.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class GovmatchId implements Serializable {

    @Column(name="modernname")
    private String mname;

    @Column(name="oldname")
    private String oname;


}
