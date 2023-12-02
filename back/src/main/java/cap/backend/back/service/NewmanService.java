package cap.backend.back.service;


import cap.backend.back.api.AzureApi;
import cap.backend.back.api.KrPediaApi;
import cap.backend.back.api.SilLokApi;
import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.domain.dto.SilokDocument;
import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.repository.*;
import cap.backend.back.service.VirtualService;
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

@Component
@RequiredArgsConstructor
public class NewmanService {

    private final PersonRepository personrepository;
    private final KrPediaRepository krPediaRepository;
    private final SilokRepository silokRepository;
    private final GptRepository gptRepository;
    private final GovRepository govRepository;

    private final KrPediaApi krPediaApi;
    private final SilLokApi silLokApi;
    private final AzureApi azureApi;

    private final VirtualService virtualService;

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
    public void doNewmanSetting(String name) throws IOException, ParseException, NoSuchAlgorithmException, KeyManagementException { //홍길동(洪吉洞) 형식으로 입력

        String[] pediaInfo=krPediaApi.getKrpediaInfo(name);
        List<SilokDocument> silokInfo=silLokApi.SilokExtractor(name);

        String[] namesetting = getNameSetting(name);
        String surnameHangul=namesetting[1];
        String surnameHanja=namesetting[0];
        String clanHangul=pediaInfo[6].split("\\(")[0];



        Clan clan=new Clan();
        ClanId clanid=new ClanId();

        Krpedia krpedia=new Krpedia();

        Person person=new Person();



        Govsequence[] govsequences=new Govsequence[5];
        for(int i=0;i<5;i++) {
            govsequences[i]=new Govsequence();
        }

        Privatehistory[] phistories=new Privatehistory[6];
        for(int i=0;i<6;i++) {
            phistories[i]=new Privatehistory();
        }

        Lifesummary lifesummary = new Lifesummary();
        Mbti mbti =new Mbti();

        //^^gpt 관련 4개 엔티티






        int base=surnameHangul.charAt(0)-'가';

        int choIdx = base / (21 * 28);
        char cho=CHO[choIdx];
        surnameHangul=surnameHangul.substring(0,1);
        surnameHanja=surnameHanja.substring(0,1);
        clanid.setClanHangul(clanHangul);
        clanid.setSurnameHanja(surnameHanja);
        clanid.setSurnameHangul(surnameHangul);

        clan.setClanid(clanid);
        clan.setCho(cho);

        personrepository.saveClan(clan);

        //^^ 데모인물의 가문 설정 ^^


        person.setName(name);
        person.setClan(clan);
        person.setPersonpicture("resources/rawdata/ggg");
        person.setBirthyear(pediaInfo[0]);
        person.setDeathyear(pediaInfo[1]);



        personrepository.save(person); //krpedia보다 먼저 나와야함





        person.setKrpedia(krpedia); //person -> krpedia


        // ^^ 인물 저장 ^^


        krpedia.setName(name);



        krpedia.setBirthyear(pediaInfo[0]);
        krpedia.setDeathyear(pediaInfo[1]);
        krpedia.setClanHangul(clanHangul);
        krpedia.setSurnameHangul(surnameHangul);
        krpedia.setSurnameHanja(surnameHanja);

        krpedia.setPersonpicture("picture sdfsdf");

        krpedia.setDefinition(pediaInfo[3]);
        krpedia.setDescription(pediaInfo[4]);
        krpedia.setMaintext(pediaInfo[5]);


        krpedia.setPerson(person); //krpedia -> person




        krPediaRepository.save(krpedia);

        //^^ 데모인물의 Krpedia ^^
        for(int i=0;i<5;i++) {
            govsequences[i].setKrpedia(krpedia);
            gptRepository.save(govsequences[i]);
        }
        for(int i=0;i<6;i++) {
            phistories[i].setKrpedia(krpedia);
            gptRepository.save(phistories[i]);
        }
        lifesummary.setKrpedia(krpedia);
        gptRepository.save(lifesummary);

        mbti.setKrpedia(krpedia);
        gptRepository.save(mbti);



        int numOfSilok=silokInfo.size();
        String govInfo="";
        for(int i=0;i<numOfSilok;i++) {
            Silok silok=new Silok();
            silok.setEventyear(Integer.parseInt(silokInfo.get(i).getPublicationYear()));
            silok.setContents(silokInfo.get(i).getContent());
            // govInfo+=silok.getContents();


            silok.setP_id(personrepository.findPersonInDBByName(name).getId()); //silok->person

            silokRepository.save(silok);
        }



        String orig_govsequence="we";
        govInfo=krpedia.getDefinition()+krpedia.getDefinition()+krpedia.getMaintext();
        orig_govsequence=azureApi.getGovsequence(govInfo);

        String[] gov_parts=orig_govsequence.split(",");
        List<String> govseq=new ArrayList<>();

        for(String part:gov_parts) {
            String[] splitPart=part.split(":");
            if(splitPart.length>1) {
                govseq.add(splitPart[1].trim());
            }
        }

        for(int i=0;i<govseq.size();i++) {
            if(i>=5) break;
            else {
                govsequences[i].setSequnce_num(i+1);

                if(govRepository.findOldgov(govseq.get(i))!=null) {
                    govsequences[i].setOldgov(govRepository.findOldgov(govseq.get(i)));
                }

                else {
                    Oldgov temp=new Oldgov();

                    Govmatch tmatch=new Govmatch();

                    tmatch.setModerngov(govRepository.findModerngov("현대미상"));
                    tmatch.setOldgov(temp);

                    temp.setName(govseq.get(i)+"(현대미상)");
                    temp.setIswarrior(false);
                    temp.setRank("종9품");
                    temp.setGovmatches(null);


                    govRepository.save(temp);
                    govRepository.save(tmatch);
                    govsequences[i].setOldgov(temp);
                }




                govsequences[i].setKrpedia(krpedia);




            }
        }




      //  ^^ gpt 4개중 첫번째, Govsequence 입력


        lifesummary.setContents(azureApi.getLifesummary(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
                                                        silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name));


        //^^ gpt 4개중 두번째, lifesummary 입력


        String orig_MBTI="asdf";
        orig_MBTI=azureApi.getMBTI(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
                silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name);
        String[] real_mbti=orig_MBTI.split("\\]");
        mbti.setContents(real_mbti[1]);

        mbti.setMbti(real_mbti[0].substring(real_mbti[0].length()-4,real_mbti[0].length()));





        // ^^ gpt 4개중 세번쨰, Mbti 입력

        String orig_phistory="asd";
        orig_phistory=azureApi.getPrivateHistory(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
                silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name);

        String[] ph_parts=orig_phistory.split("&");
        List<String> phistory_year=new ArrayList<>();
        List<String> phistory_content=new ArrayList<>();

        for(String part:ph_parts) {
            String[] splitPart=part.split(":");
            if(splitPart.length>1) {
                phistory_year.add(splitPart[0].substring(0,splitPart[0].length()-1));
                phistory_content.add(splitPart[1]);
            }
        }

        for(int i=0;i<phistory_year.size();i++) {
            if(i>=6) break;
            else {

                phistories[i].setEventyear(Integer.parseInt(phistory_year.get(i).trim()));

                phistories[i].setContents(phistory_content.get(i));


            }
        }



        //^^ gpt클래스 4개중 네번째, privatehistory 입력



        //^^ gpt 4가지 클래스 넣기 ^^
        Long temp=personrepository.findPersonInDBByName(name).getId();

        Integer[] abilities=virtualService.getAbilityById(temp); //id 를 요구하므로 save 이후에 나와야 함 + govsequence가 저장된 이후에 필요하므로 뒤에 등장

        person.setTong(abilities[0]);
        person.setMu(abilities[1]);
        person.setJi(abilities[2]);
        person.setJung(abilities[3]);
        person.setMae(abilities[4]);


    }






}
