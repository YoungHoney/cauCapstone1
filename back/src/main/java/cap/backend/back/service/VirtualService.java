package cap.backend.back.service;

import cap.backend.back.domain.Person;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.preprocessing.p_gov.pre_govmatch;
import cap.backend.back.repository.GovRepository;
import cap.backend.back.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class VirtualService {

    private final GovRepository govRepository;
    private final PersonRepository personRepository;
    private final RealService realService;



    public Integer[] getAbilityById(Long id) {
        Map<Integer,String> map=realService.findGovSequenceById(id);
        Random rd=new Random(id);

        int oldgovIter=1;
        Oldgov resultOg=null;
        Integer[] result=new Integer[5]; //통 무 지 정 매 순서
        Integer tong;
        Integer Mu;
        Integer Ji;
        Integer Jung;
        Integer Mae;
        int rankInterval=5;


        while(map.get(oldgovIter)!=null) {
            Oldgov og=govRepository.findOldgov(map.get(oldgovIter));
            pre_govmatch.OLDRANK or= pre_govmatch.OLDRANK.valueOf(og.getName());
            if(resultOg==null) {
                resultOg=og;
            }
            if(pre_govmatch.OLDRANK.valueOf(resultOg.getName()).getI()<=or.getI()) {
                resultOg=og;
            }



            oldgovIter++;
        }

        if(resultOg.isIswarrior()) { //무관, 통솔 무력  관여
            tong=(Integer)(pre_govmatch.OLDRANK.valueOf(resultOg.getName()).getI()*(95/26));
            Mu=(Integer)(pre_govmatch.OLDRANK.valueOf(resultOg.getName()).getI()*(95/26));
            Integer randNum=rd.nextInt(2*rankInterval-1)-rankInterval;
            tong+=randNum;
            Mu-=randNum; //그냥 적당히 연산

            Ji=rd.nextInt(100);
            Jung=rd.nextInt(100);
            Mae=rd.nextInt(100);


        } else { //문관 지력 정치력  관여

            tong=rd.nextInt(100);
            Mu=rd.nextInt(100);

            Ji=(Integer)(pre_govmatch.OLDRANK.valueOf(resultOg.getName()).getI()*(95/26));
            Jung=(Integer)(pre_govmatch.OLDRANK.valueOf(resultOg.getName()).getI()*(95/26));
            Integer randNum=rd.nextInt(2*rankInterval-1)-rankInterval;
            Ji+=randNum;
            Jung-=randNum; //그냥 적당히 연산

            Mae=rd.nextInt(100);
        }



        result[0]=tong;
        result[1]=Mu;
        result[2]=Ji;
        result[3]=Jung;
        result[4]=Mae;
        return result;

    }


}
