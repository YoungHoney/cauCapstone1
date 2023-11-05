package cap.backend.back.repository;

import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GptRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(Govsequence g) {
        em.persist(g);
    }

    public void save(Lifesummary l) {
        em.persist(l);
    }

    public void save(Mbti m) {
        em.persist(m);
    }

    public void save(Privatehistory p) {
        em.persist(p);
    }

    public List<Govsequence> findSequencesById(Long id) {
        return em.createQuery("select g from Govsequence g where g.id=:ID",Govsequence.class)
                .setParameter("ID",id)
                .getResultList();
    }



    public List<Privatehistory> findPrivateHistoriesById(Long id) {
        return em.createQuery("select h from Privatehistory h where h.krpedia.person.id=:ID", Privatehistory.class)
                .setParameter("ID",id)
                .getResultList();
    }

    public Lifesummary findLifeSummaryById(Long id) {
        return em.createQuery("select l from Lifesummary l where l.krpedia.person.id=:ID", Lifesummary.class)
                .setParameter("ID",id)
                .getSingleResult();
    }

    public Mbti findMbtiById(Long id) {
        return em.createQuery("select m from Mbti m where m.krpedia.person.id=:ID", Mbti.class)
                .setParameter("ID",id)
                .getSingleResult();
    }





}
