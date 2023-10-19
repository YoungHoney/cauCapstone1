package cap.backend.back.repository;


import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Person findPersonInDBByName(String name) { //name은 PK가 아니므로 find로 못찾음
        try {
            Person entity = em.createQuery("SELECT s FROM Person s WHERE s.name = :NAME", Person.class)
                    .setParameter("NAME", name)
                    .getSingleResult();
            return entity;
            // use entity...
        } catch (NoResultException nre) {
            // Handle case where no result is found
            return null;
        } catch (NonUniqueResultException nure) {
            // Handle case where more than one result is found
            return null;
        } catch (PersistenceException pe) {
            // Handle general database errors
            return null;

        }
    }

    public Person findPersonInPediaByName(String name) { //krpedia도 어쨋든 DB에 있어서 무용할수도 있음
        try {
            Person entity = em.createQuery("SELECT s FROM Krpedia s WHERE s.name = :NAME", Person.class)
                    .setParameter("NAME", name)
                    .getSingleResult();
            return entity;
            // use entity...
        } catch (NoResultException nre) {
            // Handle case where no result is found
            return null;
        } catch (NonUniqueResultException nure) {
            // Handle case where more than one result is found
            return null;
        } catch (PersistenceException pe) {
            // Handle general database errors
            return null;

        }

    }


    public List<Person> findPersonsByClan(Clan clan) {

        return em.createQuery("select p from Person p where p.clan=:Clan",Person.class)
                .setParameter("Clan",clan)
                .getResultList();

    }


    public List<Clan> findClansByLetter(String letter) {
        return em.createQuery("select c from Clan c where c.clanid.surnameHangul=:Letter",Clan.class)
                .setParameter("Letter",letter)
                .getResultList();
    }



//    public List<String> findModernsByOld(String oldname) {
//        return em.createQuery("select i.moderngov.name from Govmatch i where i.oldgov.name=:oldName", String.class)
//                .setParameter("oldName",oldname)
//                .getResultList();
//    }










}
