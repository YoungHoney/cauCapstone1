package cap.backend.back.domain.factory;

import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import org.springframework.stereotype.Component;

@Component
public class KrpediaFactory {
    public static Krpedia create(String name, String[] pediaInfo, Person person) {
        Krpedia k = new Krpedia();
        k.setName(name);
        k.setBirthyear(pediaInfo[0]);
        k.setDeathyear(pediaInfo[1]);
        k.setClanHangul(pediaInfo[6].split("\\(")[0]);
        k.setSurnameHangul(pediaInfo[1].substring(0, 1));
        k.setSurnameHanja(pediaInfo[0].substring(0, 1));
        k.setPersonpicture(pediaInfo[7]);
        k.setDefinition(pediaInfo[3]);
        k.setDescription(pediaInfo[4]);
        k.setMaintext(pediaInfo[5]);
        k.setPerson(person);
        return k;
    }
}
