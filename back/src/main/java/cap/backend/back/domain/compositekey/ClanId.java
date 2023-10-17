package cap.backend.back.domain.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;


@Data
@Embeddable
public class ClanId implements Serializable {

    @Column(length=2)
    private String clanHangul;
    @Column(length=2)
    private String surnameHanja;
    @Column(length=2)
    private String surnameHangul;




}
