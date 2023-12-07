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
import java.util.regex.Pattern;

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
    public Long doNewmanSetting(String name) throws Exception { //홍길동(洪吉洞) 형식으로 입력

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

//동적배열로 전환하자....

        List<Govsequence> govsequences=new ArrayList<>();

        List<Privatehistory> phistories=new ArrayList<>();

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




        if(personrepository.findClanByWholeName(clanHangul+surnameHangul)==null) {
            personrepository.saveClan(clan);
        }



        //^^ 데모인물의 가문 설정 ^^


        person.setName(name);
        person.setClan(clan);
        person.setPersonpicture(pediaInfo[7]);
        person.setBirthyear(pediaInfo[0]); //처치필요
        person.setDeathyear(pediaInfo[1]);
        person.setJa(pediaInfo[8]);
        person.setHo(pediaInfo[9]);
        person.setSiho(pediaInfo[10]);


        System.out.println("person.getName() = " + person.getName());
        System.out.println("person.getClan().getCho() = " + person.getClan().getCho());
        

        personrepository.save(person); //krpedia보다 먼저 나와야함





        person.setKrpedia(krpedia); //person -> krpedia
        System.out.println("person.getKrpedia() = " + person.getKrpedia());


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



        lifesummary.setKrpedia(krpedia);
        gptRepository.save(lifesummary);

        mbti.setKrpedia(krpedia);
        gptRepository.save(mbti);





        String govInfo="";

        String orig_govsequence="we";
        govInfo=krpedia.getDefinition()+krpedia.getDefinition()+krpedia.getMaintext();
        orig_govsequence=azureApi.getGovsequence(govInfo);

        int upsm_count=0;

        String[] gov_parts=orig_govsequence.split(",");
        List<String> govseq=new ArrayList<>();

        for(int i=0;i<gov_parts.length;i++) {
            if(i<5) { //최대 5개만
                String part=gov_parts[i];
                String[] splitPart=part.split(":");
                if(splitPart.length>1) {
                    govseq.add(splitPart[1].trim());
                    Govsequence temp_govseq=new Govsequence();
                    temp_govseq.setKrpedia(krpedia);
                    govsequences.add(temp_govseq);
                    System.out.println("govseq.get(i) = " + govseq.get(i));
                    gptRepository.save(temp_govseq);

                } else {
                    govseq.add("없음");
                    Govsequence temp_govseq=new Govsequence();
                    temp_govseq.setKrpedia(krpedia);
                    govsequences.add(temp_govseq);
                    System.out.println("else govseq.get(i) = " + govseq.get(i));


                    if(upsm_count==0) {
                        gptRepository.save(temp_govseq);
                        upsm_count=1;
                    }

                }
            }

        }
        System.out.println("govseq.size() = " + govseq.size());

        for(int i=0;i<govseq.size();i++) {


                govsequences.get(i).setSequnce_num(i+1);
                System.out.println("sadf");

                if(govRepository.findOldgov(govseq.get(i))!=null) {

                    govsequences.get(i).setOldgov(govRepository.findOldgov(govseq.get(i)));
                    System.out.println("있어요"+ govsequences.get(i).getOldgov().getName());
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



                    if(govRepository.findOldgov(temp.getName())==null) {
                        govRepository.save(temp);
                        govRepository.save(tmatch);
                        govsequences.get(i).setOldgov(temp);

                    }
                    System.out.println("없어요"+temp.getName());

                }


            }

        int numOfSilok=silokInfo.size();
        System.out.println("numOfSilok = " + numOfSilok);

        for(int i=0;i<numOfSilok;i++) {
            Silok silok=new Silok();
            silok.setEventyear(Integer.parseInt(silokInfo.get(i).getPublicationYear()));
            silok.setContents(silokInfo.get(i).getContent());
            // govInfo+=silok.getContents();

            // System.out.println("name = " + name);

            System.out.println("실록 내용 silok.getContents() = " + silok.getContents());
            silok.setP_id(personrepository.findPersonInDBByName(name).getId()); //silok->person

            System.out.println("넌뭐냐 silok.getP_id() = " + silok.getP_id());
            silokRepository.save(silok);
        }





      //  ^^ gpt 4개중 첫번째, Govsequence 입력

        String orig_ls="";
        String orig_ls_food=krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext();



        for(int i=0;i<silokInfo.size();i++) {
            orig_ls_food+=silokInfo.get(i).getContent();
        }
        orig_ls=azureApi.getLifesummary(orig_ls_food,name);

        lifesummary.setContents(orig_ls);


        //^^ gpt 4개중 두번째, lifesummary 입력

        String orig_MBTI="";
        String orig_MBTI_food=krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext();
        for(int i=0;i<silokInfo.size();i++) {
            orig_MBTI_food+=silokInfo.get(i).getContent();
        }
        orig_MBTI=azureApi.getMBTI(orig_MBTI_food,name);

        String[] real_mbti=orig_MBTI.split("\\]");
        mbti.setContents(real_mbti[1]);

        mbti.setMbti(real_mbti[0].substring(real_mbti[0].length()-4,real_mbti[0].length()));





        // ^^ gpt 4개중 세번쨰, Mbti 입력



        String orig_phistory="asd";
       // System.out.println("silokInfo.size() = " + silokInfo.size());
        String orig_phistory_food=krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext();
        for(int i=0;i<silokInfo.size();i++) {
            orig_phistory_food+=silokInfo.get(i).getContent();
        }
        orig_phistory=azureApi.getPrivateHistory(orig_phistory_food,name);

        String[] ph_parts=orig_phistory.split("\\$");
        List<String> phistory_year=new ArrayList<>();
        List<String> phistory_content=new ArrayList<>();

        for(int i=0;i<ph_parts.length;i++) {
            if (i < 6) {
                String part = ph_parts[i];
                String[] splitPart = part.split(":");
                if (splitPart.length > 1) { //


                    Privatehistory ph = new Privatehistory();
                    String year_info = splitPart[0].substring(0, 4);

                    Pattern pattern = Pattern.compile("\\d{4}");
                    if (pattern.matcher(year_info).matches()) {
                        phistory_year.add(year_info);
                        System.out.println("p year : " + splitPart[0].substring(0, splitPart[0].length() - 1));
                        phistory_content.add(splitPart[1]);
                        System.out.println("p content : " + splitPart[1]);


                        ph.setKrpedia(krpedia);
                        gptRepository.save(ph);
                        phistories.add(ph);
                    } else {
                        // year_info가 형식에 맞지 않으면, 이 부분에서 처리 (예: 로깅, 에러 메시지 등)
                        System.out.println("Invalid year format: " + year_info);
                    }
                }

            }
        }

        for(int i=0;i<phistory_year.size();i++) {
            phistories.get(i).setEventyear(Integer.parseInt(phistory_year.get(i).trim()));

            phistories.get(i).setContents(phistory_content.get(i));



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

        System.out.println("person.getMae() = " + person.getMae());

        System.out.println("끝물 name = " + name);
        Long result=personrepository.findPersonInDBByName(name).getId();

        System.out.println("personrepository.findPersonInDBByName(name).getId() = " + result);
        return result;

    }






}
