package cap.backend.back.preprocessing.p_gov;


import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.repository.GovRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class pre_govmatch {

private final GovRepository govRepository;

private enum MODRANK{
    제9급, 제8급, 제7급, 제6급, 제5급, 제4급, 제3급, 제2급, 제1급, 제준차관급, 제차관급, 제장관급, 제부총리급, 제총리급, 제국가원수;


}

public enum OLDRANK{
    종9품(1), 정9품(2), 종8품(3), 정8품(4), 종7품(5), 정7품(6), 종6품(8), 정6품(9), 종5품(10), 정5품(11), 종4품(12), 정4품(13), 종3품(14),
    정3품(16), 종2품(18), 정2품(20), 종1품(24), 정1품(26), 왕(28);

    private final int i;
    OLDRANK(int i) {
        this.i=i;
    }

    public int getI() {
        return i;
    }
}
@Transactional
public void doMatching() {


    List<Oldgov> allGovList=govRepository.findAllOldgov();
    List<Moderngov> allModList=govRepository.findAllModerngov();

    for(Oldgov o : allGovList) {

        for(Moderngov m : allModList) {
            Govmatch temp=new Govmatch();
            temp.setOldgov(o);

            OLDRANK OR=OLDRANK.valueOf(o.getRank());
            MODRANK MR=MODRANK.valueOf("제"+m.getRank());

            if(o.isIswarrior()==m.isIswarrior()) {
                if(OR.getI()==20&&(MR.ordinal()==10||MR.ordinal()==11)) {

                    temp.setModerngov(m);
                    temp.setOldgov(o);
                    govRepository.save(temp);
                }
                else if(OR.getI()/2==MR.ordinal()) {
                    temp.setModerngov(m);
                    temp.setOldgov(o);
                    govRepository.save(temp);
                }


            }




        }

    }


}





}
