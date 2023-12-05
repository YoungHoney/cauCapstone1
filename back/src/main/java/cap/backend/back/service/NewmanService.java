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
        person.setPersonpicture(pediaInfo[7]);
        person.setBirthyear(pediaInfo[0]); //처치필요
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

            System.out.println("name = " + name);

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
                    System.out.println("있어요"+govsequences[i].getOldgov().getName());
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
                    System.out.println("없어요"+temp.getName());
                }




                govsequences[i].setKrpedia(krpedia);




            }
        }




      //  ^^ gpt 4개중 첫번째, Govsequence 입력


//        lifesummary.setContents(azureApi.getLifesummary(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
//                                                        silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name));
        lifesummary.setContents("조선 전기에 활동한 곽순은 중요한 관리와 학자였다. 그는 1528년에 최고 학위인 식년문과에 병과로 합격한 뒤, 다양한 관직을 역임하며 공을 세웠다. 그는 성균관 박사, 호조 좌랑, 기주 관 등을 거치며 그 능력을 인정받았고, 결국 1543년에는 서천군수가 되었다. 이는 그가 참신하고 밝은 사고력을 가진 학자이자, 탁월한 리더십을 지닌 관료였음을 보여준다. 이어서 그는 사예, 사성, 장령 등의 중요한 관직을 맡으며 당시의 복잡한 정치적 상황을 능숙하게 처리하며 능력을 발휘했다.\n" +
                "\n" +
                "그의 가장 주요한 장점 중 하나는 그의 확고한 신념과 정직함이었다. 그는 정치적 중립성을 유지하면서도 자신의 정치적 입장을 분명하게 표현하는 능력을 가지고 있었다. 그의 행동은 그의 엄격한 윤리적 가치와 깊은 사회적 책임감을 반영하고 있었다.\n" +
                "\n" +
                "그는 또한 사실적이면서도 혁신적인 접근 방식을 가지고 있었다. 그는 복잡한 문제를 해결하기 위해 이론적 지식과 경험적 지혜를 결합하였다. 그의 성공적인 경력은 그의 탁월한 리더십 능력과 학식을 증명하면서도, 그이 지혜롭고 효과적인 방식으로 사회와 정치 문제를 해결하는 능력을 보여주었다.\n" +
                "\n" +
                "총론적으로 볼 때, 곽순은 그의 시대에 매우 중요한 인물이었다. 그는 학문과 정치 모두에서 놀라운 업적을 달성하였고, 그의 공헌은 그의 시대를 넘어 우리 시대까지 영향을 미쳤다. 그는 뛰어난 학자이자, 탁월한 관료였으며, 그의 능력과 업적은 그를 조선 시대의 중요한 인물로 만들었다.\n");


        //^^ gpt 4개중 두번째, lifesummary 입력


        String orig_MBTI="결과예시: [INTJ] 곽순의 기록을 통해 몇 가지 성격적 특성을 추측해 볼 수 있습니다:\n" +
                "\n" +
                "그는 교리, 봉상시정, 사간 등을 역임한 문신으로, 다양한 직책을 맡아 국가를 위해 노력했습니다. 이는 그가 계획적이고 체계적인 사고를 가지고 있었을 가능성을 나타냅니다.\n" +
                "그는 복잡한 정치적 환경에서도 자신의 역할을 충실히 수행했습니다. 이는 그가 독립적이고 결단력 있는 성격을 가지고 있었을 가능성을 보여줍니다.\n" +
                "그는 윤임과 같은 반역자들에 대한 정보를 제공하고, 이를 통해 국가의 안정을 유지하는 데 기여했습니다. 이는 그가 논리적이고 분석적인 사고를 가지고 있었을 가능성을 나타냅니다.\n" +
                "이러한 특성을 볼 때, 곽순은 MBTI에서 I(Introverted, 내향적), N(Intuitive, 직관적), T(Thinking, 사고적), J(Judging, 판단적) 유형에 속할 가능성이 있습니다. 예를 들어 INTJ 유형은 '전략가'로 불리며, 독립적이고 분석적인 사고를 가지고 있으며, 계획을 세우고 이를 실행하는 데 능숙합니다. 이러한 유형은 곽순이 보여준 행동과 일치하는 경향이 있습니다.\n";
//        orig_MBTI=azureApi.getMBTI(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
//                silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name);

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
