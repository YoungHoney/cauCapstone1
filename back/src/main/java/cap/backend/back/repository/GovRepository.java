package cap.backend.back.repository;


import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.govrank.Oldgov;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GovRepository {

    @PersistenceContext
    EntityManager em;



    public String save(Moderngov gov) {
        em.persist(gov);
        return gov.getName();

    }

    public String save(Oldgov gov) {
        em.persist(gov);
        return gov.getName();
    }


    public Long save(Govmatch govmatch) {
        em.persist(govmatch);
        return govmatch.getId();
    }

    public Moderngov findModerngov(String modernname) {
        return em.find(Moderngov.class,modernname);
    }

    public Oldgov findOldgov(String oldname) {
        return em.find(Oldgov.class,oldname);
    }


    public List<String> findModernsByOld(String oldname) {
        return em.createQuery("select i.moderngov.name from Govmatch i where i.oldgov.name=:oldName", String.class)
                .setParameter("oldName",oldname)
                .getResultList();
    }

    public List<Oldgov> findAllOldgov() {
        return em.createQuery("select i from Oldgov i",Oldgov.class)
                .getResultList();
    }

    public List<Moderngov> findAllModerngov() {
        return em.createQuery("select i from Moderngov i",Moderngov.class)
                .getResultList();
    }



}
