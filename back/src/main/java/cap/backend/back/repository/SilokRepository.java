package cap.backend.back.repository;

import cap.backend.back.domain.Silok;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


@Repository
public class SilokRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Silok silok) {
        em.persist(silok);
    }


}
