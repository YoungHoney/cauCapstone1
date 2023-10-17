package cap.backend.back.domain;


import cap.backend.back.domain.compositekey.ClanId;
import jakarta.persistence.*;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Clan {

    @EmbeddedId
    @Column(length=2)
    private ClanId clanid;



    @OneToMany(mappedBy = "clan")
    private List<Person> persons=new ArrayList<>();


    public Clan(){}


}
