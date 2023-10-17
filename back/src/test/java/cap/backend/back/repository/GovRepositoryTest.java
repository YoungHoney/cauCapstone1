package cap.backend.back.repository;

import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.govrank.Oldgov;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
class GovRepositoryTest {

    @Autowired
    GovRepository govRepository;



    @Test
    @Transactional
    void 매칭_잘되는지_테스트() {
        Oldgov ogov=new Oldgov();
        ogov.setName("예조판서");
        ogov.setRank("정2품");
        ogov.setIswarrior(false);

        govRepository.save(ogov);

        Moderngov mgov1=new Moderngov();
        mgov1.setName("법무부장관");
        mgov1.setRank("장관급");
        mgov1.setPersonname("한동훈");
        mgov1.setIswarrior(false);

        govRepository.save(mgov1);

        Moderngov mgov2=new Moderngov();
        mgov2.setName("국방부장관");
        mgov2.setRank("장관급");
        mgov2.setPersonname("신원식");
        mgov2.setIswarrior(true);

        govRepository.save(mgov2);

        Govmatch govm1=new Govmatch();
        govm1.setOldgov(ogov);
        govm1.setModerngov(mgov1);

        govRepository.save(govm1);

        Govmatch govm2=new Govmatch();
        govm2.setOldgov(ogov);
        govm2.setModerngov(mgov2);

        govRepository.save(govm2);


        List<String> Li1=govRepository.findModernsByOld(ogov.getName());


        assertEquals("법무부장관",Li1.get(0));
        assertEquals("국방부장관",Li1.get(1));




    }





}