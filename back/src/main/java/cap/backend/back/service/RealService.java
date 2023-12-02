package cap.backend.back.service;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.preprocessing.p_gov.pre_govmatch;
import cap.backend.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RealService {

    private final KrPediaRepository krpediarepository;
    private final PersonRepository personrepository;
    private final GptRepository gptrepository;

    private final OldEventsRepository oldeventsrepository;
    private final GovRepository govrepository;

    public Person findOne(Long id) {
        return personrepository.findOne(id);
    }

    public Map<Integer,String> findPrivateHistoriesById(Long id) {
        List<Privatehistory> orgList= gptrepository.findPrivateHistoriesById(id);
        Map<Integer,String> result=new HashMap<>();

        for(Privatehistory p:orgList) {
            result.put(p.getEventyear(),p.getContents());
        }

        return result;
    }

    public String findLifeSummaryById(Long id) {
        return gptrepository.findLifeSummaryById(id).getContents();
    }

    public Map<Integer,String> findOldEventsById(Long id) {
        List<Oldevents> orgList=oldeventsrepository.findOldEventsById(id);
        Map<Integer,String> result=new HashMap<>();

        for(Oldevents o:orgList) {
            result.put(o.getYear(),o.getName());
        }
        return result;
    }

    public Map<Integer,String> findGovSequenceById(Long id) {

        List<Govsequence> orgseq=govrepository.findGovSequenceById(id);

        Collections.sort(orgseq,new GovsequenceComparator()); //sequence의 순서 보장


        int All_gov=orgseq.size(); //인물이 거친 모든 관직의 수
        int seq=1;


        Random random=new Random(id);


        Map<Integer,String> result=new HashMap<>();
        for(Govsequence g : orgseq) {





            if(!g.getOldgov().getName().contains("현대미상")) {


                List<String> mgovList= govrepository.findModernsByOld(g.getOldgov().getName());

                int randInt=random.nextInt(mgovList.size());
                // result.put(g.getOldgov().getName(),mgovList.get(randInt));
                result.put(seq++,g.getOldgov().getName()+","+mgovList.get(randInt));

            }
            else {
                result.put(seq++,g.getOldgov().getName()+","+"대상없음");
            }

        }

        return result;
    }

    class GovsequenceComparator implements Comparator<Govsequence> {

        @Override
        public int compare(Govsequence o1, Govsequence o2) {
            if(o1.getSequnce_num()>o2.getSequnce_num()) {
                return 1;
            }
            else if(o1.getSequnce_num()<o2.getSequnce_num()) {
                return -1;
            }
            else return 0;
        }
    }

    public String findPictureById(Long id){
        return personrepository.findPictureById(id);
    }

    public String findDefById(Long id) { return krpediarepository.findDefById(id);}







}
