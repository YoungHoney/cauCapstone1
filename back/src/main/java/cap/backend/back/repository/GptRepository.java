package cap.backend.back.repository;

import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Privatehistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GptRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Govsequence> findSequencesById(Long id) {
        return em.createQuery("select g from Govsequence g where g.id=:ID",Govsequence.class)
                .setParameter("ID",id)
                .getResultList();
    }

    public Privatehistory findHistoriesById(Long id) {
        return em.find(Privatehistory.class,id);
    }

    public Lifesummary findLifesummaryById(Long id) {
        return em.find(Lifesummary.class,id);

    }
//----------^^^ 실화기반 페이지에서 사용^^^____________________





}
