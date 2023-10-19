package cap.backend.back.repository;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class KrPediaRepository {

    @PersistenceContext
    private EntityManager em;


    public Krpedia findById(Long id) {
        return em.createQuery("SELECT k FROM Krpedia k WHERE k.person.id = :ID", Krpedia.class)
                .setParameter("ID", id)
                .getSingleResult();
    }


}