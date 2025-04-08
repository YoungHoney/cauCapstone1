package cap.backend.back.service;


import cap.backend.back.api.AiApi;
import cap.backend.back.api.KrPediaApi;
import cap.backend.back.api.SilLokApi;
import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.domain.dto.SilokDocument;
import cap.backend.back.domain.factory.ClanFactory;
import cap.backend.back.domain.factory.KrpediaFactory;
import cap.backend.back.domain.factory.PersonFactory;
import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.repository.*;
import cap.backend.back.service.VirtualService;
import cap.backend.back.util.NameInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class NewmanService {

    private final PersonRepository personrepository;
    private final KrPediaRepository krPediaRepository;
    private final SilokRepository silokRepository;
    private final GptRepository gptRepository;
    private final GovRepository govRepository;
    private final ClanRepository clanRepository;

    private final KrPediaApi krPediaApi;
    private final SilLokApi silLokApi;
    private final AiApi aiApi;

    private final VirtualService virtualService;
    private final GptResultService gptResultService;
    private final SilokService silokService;

    @Transient
    @JsonIgnore
    private static final char[] CHO =
            {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};


    private String[] getNameSetting(String name) {
        String[] result=new String[3];

        String[] temp=name.split("\\(");
        
        result[0]=temp[1];
        result[0]=result[0].substring(0,result[0].length()-1);
        result[1]=temp[0];

        return result;
    }

    @Transactional
    public Long doNewmanSetting(String name) throws Exception {
        NameInfo info = NameInfo.parse(name); // ex) 홍길동(洪吉洞) → {hangul, hanja}
        String[] pediaInfo = krPediaApi.getKrpediaInfo(name);
        System.out.println("KRPEDIA 종료 ");
        List<SilokDocument> siloks = silLokApi.SilokExtractor(name);
        System.out.println("SILOKEXTRACTOR 종료 ");

        // Clan 생성 및 저장
        String clanHangul = pediaInfo[6].split("\\(")[0];
        Clan clan = clanRepository.findById(info.toClanId(clanHangul))
                .orElseGet(() -> clanRepository.save(ClanFactory.create(clanHangul, info.hangul, info.hanja)));
        System.out.println("clan 끝 ");

        // Person 생성 및 저장
        Person person = PersonFactory.create(name, pediaInfo, clan);
        personrepository.save(person);
        System.out.println("person 끝 ");

        // Krpedia 생성 및 저장
        Krpedia krpedia = KrpediaFactory.create(name, pediaInfo, person);
        krPediaRepository.save(krpedia);
        System.out.println("KRPEDIA 저장 끝 ");

        // GPT 결과 생성 및 저장
        gptResultService.generateGovsequence(krpedia);
        System.out.println("govseq 생성 및 저장 완료 ");
        gptResultService.generateLifesummary(krpedia, siloks);
        System.out.println("lifesumm 생성 및 저장 완료 ");
        gptResultService.generateMbti(krpedia, siloks);
        System.out.println("mbti 생성 및 저장 완료 ");
        gptResultService.generatePrivateHistory(krpedia, siloks);
        System.out.println("privateHistory 생성 및 저장 완료 ");


        // 실록 저장
        silokService.saveAll(person, siloks);
        System.out.println("실록 저장 완료 ");

        // 능력치 계산 및 반영
        Integer[] abilities = virtualService.getAbilityById(person.getId());
        PersonFactory.applyAbilities(person, abilities);


        return person.getId();
    }


}
