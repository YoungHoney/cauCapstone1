package cap.backend.back.repository;

import cap.backend.back.domain.Silok;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SilokRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Silok silok) {
        em.persist(silok);
    }

    public List<Silok> getSiloksBypersonId(Long personid) {
        return em.createQuery("select s from Silok s where s.p_id=:personId",Silok.class)
                .setParameter("personId",personid)
                .getResultList();
    }




}
