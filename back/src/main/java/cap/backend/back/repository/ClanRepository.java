package cap.backend.back.repository;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.compositekey.ClanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<Clan, ClanId> {

    @Query("SELECT c FROM Clan c WHERE c.clanid.clanHangul = :clanHangul AND c.clanid.surnameHangul = :surnameHangul")
    Clan findByClanHangulAndSurnameHangul(@Param("clanHangul") String clanHangul,
                                          @Param("surnameHangul") String surnameHangul);
}
