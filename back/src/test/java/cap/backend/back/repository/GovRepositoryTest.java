package cap.backend.back.repository;

import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.govrank.Oldgov;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Rollback(false)
class GovRepositoryTest {

    @Autowired //before each에서 새로 넣어줘서 리프레시
    GovRepository govRepository;

    @AfterEach
    void setting() {
        govRepository=new GovRepository();


    }



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

//    @Test
//    @Transactional
//    void 사진_들어가나_테스트() throws IOException {
//        Moderngov mgov1=new Moderngov();
//
//        mgov1.setName("법무부장관");
//        mgov1.setRank("장관급");
//        mgov1.setPersonname("한동훈");
//        mgov1.setIswarrior(false);
//
//        try (InputStream input = getClass().getResourceAsStream("/rawdata/moderngov/HanDongHun.png")) {
//            byte[] photoData = IOUtils.toByteArray(input);
//            mgov1.setPersonpicture(photoData);
//
//            govRepository.save(mgov1);
//        } catch (IOException e) {
//            // 이미지 로딩 또는 변환 중에 문제가 발생했습니다.
//            e.printStackTrace();
//        }
//
//       // System.out.println("findModerngov(\"\") = " + govRepository.findModerngov("법무부장관"));
//        assertEquals(mgov1.getPersonpicture(),govRepository.findModerngov("법무부장관").getPersonpicture());
//
//
//
//
//    }







}