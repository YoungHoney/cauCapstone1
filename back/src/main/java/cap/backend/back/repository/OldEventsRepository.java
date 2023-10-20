package cap.backend.back.repository;


import cap.backend.back.domain.Oldevents;
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

}
