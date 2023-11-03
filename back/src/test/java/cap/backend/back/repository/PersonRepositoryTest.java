package cap.backend.back.repository;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.compositekey.ClanId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
class PersonRepositoryTest {

    @Autowired
    PersonRepository personrepository;

    @Test
    @Transactional
    void 사람저장_잘되나_테스트() {

        Person person1=new Person();
        person1.setName("손흥민");


        personrepository.save(person1);


        Person isPerson1=personrepository.findOne(1L);

        assertEquals(person1.getName(), isPerson1.getName());


    }

    @Test
    @Transactional
    void 가문_저장() {
        ClanId clan1=new ClanId();
        clan1.setClanHangul("해평");
        clan1.setSurnameHangul("윤");
        clan1.setSurnameHanja("海平");

        ClanId clan2=new ClanId();
        clan2.setClanHangul("파평");
        clan2.setSurnameHangul("윤");
        clan2.setSurnameHanja("巴平");

        Clan clan_1=new Clan();
        clan_1.setClanid(clan1);
        clan_1.setCho('ㅇ');

        Clan clan_2=new Clan();
        clan_2.setClanid(clan2);
        clan_2.setCho('ㅇ');


        personrepository.saveClan(clan_1);
        personrepository.saveClan(clan_2);

        List<Clan> ls=personrepository.findClansByLetter('ㅇ');


        assertEquals("해평", ls.get(0).getClanid().getClanHangul());
        assertEquals("파평",ls.get(1).getClanid().getClanHangul());
    }


}