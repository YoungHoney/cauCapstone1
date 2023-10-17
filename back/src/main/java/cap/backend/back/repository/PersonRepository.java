package cap.backend.back.repository;


import cap.backend.back.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Person person) {
        em.persist(person);
        return person.getId();
    }

    public Person findOne(Long id) {
        return em.find(Person.class,id);
    }




}
