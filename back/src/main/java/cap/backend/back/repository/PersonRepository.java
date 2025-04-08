package cap.backend.back.repository;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.compositekey.ClanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    Person findPersonInDBByName(@Param("name") String name);

    @Query("SELECT p FROM Krpedia k JOIN k.person p WHERE k.name = :name")
    Person findPersonInPediaByName(@Param("name") String name);

    List<Person> findAllByClan(Clan clan);

    @Query("SELECT c FROM Clan c WHERE c.cho = :letter")
    List<Clan> findClansByLetter(@Param("letter") char letter);

    @Query("SELECT c FROM Clan c WHERE c.clanid.clanHangul = :clanHangul AND c.clanid.surnameHangul = :surnameHangul")
    Clan findClanByWholeName(@Param("clanHangul") String clanHangul, @Param("surnameHangul") String surnameHangul);


    @Query("SELECT p.personpicture FROM Person p WHERE p.id = :id")
    String findPictureById(@Param("id") Long id);
}
