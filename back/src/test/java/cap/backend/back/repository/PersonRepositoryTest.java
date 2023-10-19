package cap.backend.back.repository;

import cap.backend.back.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Rollback(false)
class PersonRepositoryTest {

    @Autowired
    PersonRepository personrepository;

    @Test
    @Transactional
    void 사람저장_잘되나_테스트() {

        Person person1=new Person();
        person1.setName("손흥민");

        Long person1_id= personrepository.save(person1);

        Person isPerson1=personrepository.findOne(person1_id);

        assertEquals(person1.getName(), isPerson1.getName());


    }


}