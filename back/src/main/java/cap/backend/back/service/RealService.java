package cap.backend.back.service;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            result.put(p.getYear(),p.getContents());
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

    public Map<String,String> findGovSequenceById(Long id) {
        List<Govsequence> orgseq=govrepository.findGovSequenceById(id);
        Random random=new Random();

        Map<String,String> result=new HashMap<>();
        for(Govsequence g : orgseq) {
            List<String> mgovList= govrepository.findModernsByOld(g.getOldgov().getName());
            int randInt=random.nextInt(mgovList.size());
            result.put(g.getOldgov().getName(),mgovList.get(randInt));



        }

        return result;




    }

    public String findPictureById(Long id){
        return personrepository.findPictureById(id);
    }





}
