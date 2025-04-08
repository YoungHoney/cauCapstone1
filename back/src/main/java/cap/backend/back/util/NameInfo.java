package cap.backend.back.util;

import cap.backend.back.domain.compositekey.ClanId;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;


public class NameInfo {
    public final String hangul;
    public final String hanja;

    private NameInfo(String hangul, String hanja) {
        this.hangul = hangul;
        this.hanja = hanja;
    }

    public static NameInfo parse(String fullName) {
        String[] parts = fullName.split("\\(");
        return new NameInfo(parts[0], parts[1].replace(")", ""));
    }

    public ClanId toClanId(String clanHangul) {
        ClanId id = new ClanId();
        id.setClanHangul(clanHangul);
        id.setSurnameHangul(hangul.substring(0, 1));
        id.setSurnameHanja(hanja.substring(0, 1));
        return id;
    }
}
