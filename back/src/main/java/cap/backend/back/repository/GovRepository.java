package cap.backend.back.repository;


import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.domain.gptresults.Govsequence;
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
    } //관직명을 넣어야함

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

    public List<Govsequence> findGovSequenceById(Long id) {

        return em.createQuery("select s from Govsequence s where s.krpedia.person.id=:ID",Govsequence.class)
                .setParameter("ID",id)
                .getResultList();

    }



}
