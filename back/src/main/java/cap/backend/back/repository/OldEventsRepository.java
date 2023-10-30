package cap.backend.back.repository;


import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OldEventsRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Oldevents> findAll() {
        return em.createQuery("select o from Oldevents o",Oldevents.class).getResultList();
    }

    public long save(Oldevents o) {
        em.persist(o);
        return o.getId();
    }

    public List<Oldevents> findOldEventsById(Long id) {
        Person person=em.find(Person.class,id);
        Integer birth;
        Integer death;


        if(person.getBirthyear()==null && person.getDeathyear()==null) {
            return null;
        }
        if(person.getBirthyear()==null && person.getDeathyear()!=null) {

            death=person.getDeathyear();
            birth=death-100;

        }

        if(person.getBirthyear()!=null && person.getDeathyear()==null) {
            birth=person.getBirthyear();
            death=birth+100;


        }

        else {
            birth=person.getBirthyear();
            death=person.getDeathyear();

        }
        return em.createQuery("select e from Oldevents e where e.year between :start and :end",Oldevents.class)
                .setParameter("start",birth)
                .setParameter("end",death)
                .getResultList();


    }

}
