package cap.backend.back.domain;


import cap.backend.back.domain.compositekey.ClanId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;




@Entity
@Getter @Setter
public class Clan {

    @Transient
    @JsonIgnore
    private static final char[] CHO =
            {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
                    'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    @EmbeddedId
   // @Column(length=2)
    private ClanId clanid;

    @JsonIgnore
    @OneToMany(mappedBy = "clan",fetch=FetchType.EAGER)
    private List<Person> persons=new ArrayList<>();

    @JsonIgnore
    private char cho;

    public Clan(){}

    public void setChoByCho(String letter) {
        this.cho=letter.charAt(0);
        System.out.println(this.cho);
    }

    public void setChoBySurname(String surname) {
        int base=surname.charAt(0)-'가';
        int choIdx = base / (21 * 28);
        this.cho=CHO[choIdx];
    }


}

