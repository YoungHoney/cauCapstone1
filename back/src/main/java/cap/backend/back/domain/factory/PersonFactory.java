package cap.backend.back.domain.factory;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonFactory {
    public static Person create(String name, String[] pediaInfo, Clan clan) {
        Person p = new Person();
        p.setName(name);
        p.setClan(clan);
        p.setBirthyear(pediaInfo[0]);
        p.setDeathyear(pediaInfo[1]);
        p.setPersonpicture(pediaInfo[7]);
        p.setJa(pediaInfo[8]);
        p.setHo(pediaInfo[9]);
        p.setSiho(pediaInfo[10]);
        return p;
    }

    public static void applyAbilities(Person p, Integer[] ability) {
        p.setTong(ability[0]);
        p.setMu(ability[1]);
        p.setJi(ability[2]);
        p.setJung(ability[3]);
        p.setMae(ability[4]);
    }
}
