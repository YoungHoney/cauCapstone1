package cap.backend.back.preprocessing;


import cap.backend.back.api.KrPediaApi;
import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.repository.KrPediaRepository;
import cap.backend.back.repository.PersonRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DemoSetting {

    private final PersonRepository personrepository;
    private final KrPediaRepository krPediaRepository;
    @Transient
    @JsonIgnore
    private static final char[] CHO =
            {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};


    @Transactional
    public void doDemoSetting(String name,String surnameHanja,String surnameHangul, String clanHangul) throws IOException, ParseException {
        Clan clan=new Clan();
        ClanId clanid=new ClanId();
        Krpedia krpedia=new Krpedia();
        Person person=new Person();
        KrPediaApi krPediaApi=new KrPediaApi();
        String[] pediaInfo=krPediaApi.getKrpediaInfo(name);


        //^^ 변수 선언 ^^

        int base=surnameHangul.charAt(0)-'가';
        int choIdx = base / (21 * 28);
        char cho=CHO[choIdx];
        clanid.setClanHangul(clanHangul);
        clanid.setSurnameHanja(surnameHanja);
        clanid.setSurnameHangul(surnameHangul);

        clan.setClanid(clanid);
        clan.setCho(cho);

        personrepository.saveClan(clan);

        //^^ 데모인물의 가문 설정 ^^


        person.setName(name);
        person.setClan(clan);
        person.setPersonpicture("resources/rawdata/ggg");
        person.setBirthyear(Integer.parseInt(pediaInfo[0]));
        person.setDeathyear(Integer.parseInt(pediaInfo[1]));
    
        person.setTong(99); //임시값

        personrepository.save(person); //krpedia보다 먼저 나와야함
        person.setKrpedia(krpedia); //person -> krpedia


        // ^^ 데모인물 저장 ^^


        krpedia.setName(name);



        krpedia.setBirthyear(Integer.parseInt(pediaInfo[0]));
        krpedia.setDeathyear(Integer.parseInt(pediaInfo[1]));
        krpedia.setClanHangul(clanHangul);
        krpedia.setSurnameHangul(surnameHangul);
        krpedia.setSurnameHanja(surnameHanja);

        krpedia.setPersonpicture("picture sdfsdf");

        krpedia.setDefinition(pediaInfo[3]);
        krpedia.setDescription(pediaInfo[4]);
        krpedia.setMaintext(pediaInfo[5]);

        krpedia.setPerson(person); //krpedia -> person




        krPediaRepository.save(krpedia);

        //^^ 데모인물의 Krpedia ^^






    }






}
