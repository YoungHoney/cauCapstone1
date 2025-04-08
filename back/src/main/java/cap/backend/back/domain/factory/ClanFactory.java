package cap.backend.back.domain.factory;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.compositekey.ClanId;
import org.springframework.stereotype.Component;

@Component
public class ClanFactory {
    public static Clan create(String clanHangul, String surnameHangul, String surnameHanja) {
        ClanId id = new ClanId();
        id.setClanHangul(clanHangul);
        id.setSurnameHangul(surnameHangul.substring(0, 1));
        id.setSurnameHanja(surnameHanja.substring(0, 1));

        Clan clan = new Clan();
        clan.setClanid(id);
        clan.setChoBySurname(surnameHangul);

        return clan;
    }
}
