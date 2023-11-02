package cap.backend.back.domain.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;


@Data
@Embeddable
public class ClanId implements Serializable {

    @Column
    private String clanHangul;
    @Column
    private String surnameHanja;
    @Column
    private String surnameHangul;




}
