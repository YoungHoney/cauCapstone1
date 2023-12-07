package cap.backend.back.preprocessing;


import cap.backend.back.api.AzureApi;
import cap.backend.back.api.KrPediaApi;
import cap.backend.back.api.SilLokApi;
import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.domain.dto.SilokDocument;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class DemoSetting {

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


    @Transactional
    public void doDemoSetting(String name,String surnameHanja,String surnameHangul, String clanHangul) throws Exception {
        Clan clan=new Clan();
        ClanId clanid=new ClanId();

        Krpedia krpedia=new Krpedia();

        Person person=new Person();


        String[] pediaInfo=krPediaApi.getKrpediaInfo(name);
        List<SilokDocument> silokInfo=silLokApi.SilokExtractor(name);

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







        //^^ 변수 선언 ^^

        int base=surnameHangul.charAt(0)-'가';
        int choIdx = base / (21 * 28);
        char cho=CHO[choIdx];
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
        person.setBirthyear(pediaInfo[0]);
        person.setDeathyear(pediaInfo[1]);



        personrepository.save(person); //krpedia보다 먼저 나와야함





        person.setKrpedia(krpedia); //person -> krpedia


        // ^^ 데모인물 저장 ^^


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
//        System.out.println("govInfo = " + govInfo);
//
//        govInfo=krpedia.getDefinition()+krpedia.getDefinition()+krpedia.getMaintext();
//        azureApi.getGovsequence(govInfo);

        //^^데모인물의 silok 기록 등록 ^^


        switch(name) {
            case "박세채(朴世采)": {
                govsequences[0].setSequnce_num(1);
                govsequences[0].setOldgov(govRepository.findOldgov("사헌부집의(司憲府執義)"));


                govsequences[1].setSequnce_num(2);
                govsequences[1].setOldgov(govRepository.findOldgov("동부승지(同副承旨)"));


                govsequences[2].setSequnce_num(3);
                govsequences[2].setOldgov(govRepository.findOldgov("공조참판(工曹參判)"));


                govsequences[3].setSequnce_num(4);
                govsequences[3].setOldgov(govRepository.findOldgov("이조판서(吏曹判書)"));


                govsequences[4].setSequnce_num(5);
                govsequences[4].setOldgov(govRepository.findOldgov("우참찬(右參贊)"));



                break;
            }

            case "김상익(金尙翼)": {

                govsequences[0].setSequnce_num(1);
                govsequences[0].setOldgov(govRepository.findOldgov("이조좌랑(吏曹佐郞)"));

                govsequences[1].setSequnce_num(2);
                govsequences[1].setOldgov(govRepository.findOldgov("도승지(都承旨)"));

                govsequences[2].setSequnce_num(3);
                govsequences[2].setOldgov(govRepository.findOldgov("지경연사(知經筵事)"));

                govsequences[3].setSequnce_num(4);
                govsequences[3].setOldgov(govRepository.findOldgov("강화유수(江華留守)"));

                govsequences[4].setSequnce_num(5);
                govsequences[4].setOldgov(govRepository.findOldgov("봉조하(奉朝賀)"));

                break;
            }

            case "권응수(權應銖)": {
                govsequences[0].setSequnce_num(1);
                govsequences[0].setOldgov(govRepository.findOldgov("병마절도사(兵馬節度使)"));

                govsequences[1].setSequnce_num(2);
                govsequences[1].setOldgov(govRepository.findOldgov("방어사(防禦使)"));

                govsequences[2].setSequnce_num(3);
                govsequences[2].setOldgov(govRepository.findOldgov("부사직(副司直)"));

                govsequences[3].setSequnce_num(4);
                govsequences[3].setOldgov(govRepository.findOldgov("호군(護軍)"));

                govsequences[4].setSequnce_num(5);
                govsequences[4].setOldgov(govRepository.findOldgov("방어사(防禦使)"));
                break;
            }

            case "이산해(李山海)": {

                govsequences[0].setSequnce_num(1);
                govsequences[0].setOldgov(govRepository.findOldgov("이조정랑(吏曹正郞)"));

                govsequences[1].setSequnce_num(2);
                govsequences[1].setOldgov(govRepository.findOldgov("형조판서(刑曹判書)"));

                govsequences[2].setSequnce_num(3);
                govsequences[2].setOldgov(govRepository.findOldgov("우의정(右議政)"));

                govsequences[3].setSequnce_num(4);
                govsequences[3].setOldgov(govRepository.findOldgov("영돈령부사(領敦寧府事)"));

                govsequences[4].setSequnce_num(5);
                govsequences[4].setOldgov(govRepository.findOldgov("영의정(領議政)"));


                break;
            }

        }


        //^^ gpt 4개중 첫번째, Govsequence 입력
//        lifesummary.setContents(azureApi.getLifesummary(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
//                                                        silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name)); 실제로 입력할때
        switch(name) {
            case "박세채(朴世采)": {
                lifesummary.setContents("박세채는 반남 박씨 가문에서 출생하여, 성균관에서 학문을 시작하였다. 그는 이이(李珥)의 학문에 깊은 존경심을 가지고 있었으며, 이후 학자이자 정치인으로서의 삶을 시작하였다.\n" +
                        "\n" +
                        "1659년에는 중요 관직에 천거되어 익위사세마(翊衛司洗馬)가 되었으며, 당시 정치 상황에서 서인 측의 이론가로 활약하며 남인에 맞서 주장을 펼쳤다. 특히 복제사의(服制私議)를 통해 남인의 주장을 체계적으로 반박하면서 자신의 지식과 논리력을 드러냈다.\n" +
                        "\n" +
                        "박세채는 소론의 영도자로서 논리적이고 정의로운 입장을 견지하며, 이이와 성혼에 대한 문묘종사 문제의 해결에 기여했을 뿐만 아니라, 대동법과 같은 중요한 정책의 실시를 주장하여 사회 및 경제 개혁에도 큰 영향을 미쳤다.\n" +
                        "\n" +
                        "마지막으로, 박세채는 학문적으로나 정치적으로 중요한 발자취를 남긴 인물로서, 후세에 큰 존경을 받고 있는 인물이다. 그의 삶은 신념과 학문, 그리고 불굴의 정신으로 가득 찬 조선 시대의 선비의 모습을 보여준다.");




                break;
            }

            case "김상익(金尙翼)": {
                lifesummary.setContents("김상익은 조선 후기의 우수한 문신으로, 국가의 핵심을 담당하는 다양한 역할을 수행했다. 병과로의 급제 이후 지평과 정언을 맡았고, 충청도어사를 비롯해 수찬, 교리, 헌납 등의 역임을 했다. 그는 이조좌랑을 지낸 후 세자시강원필선, 대사간 등에 오르며 그 능력을 인정받았고, 경기도 관찰사와 대사헌 등도 맡았다.\n" +
                        "\n" +
                        "사은부사로 외교적 목적으로 청나라에 다녀왔고, 그 후로도 지속적으로 중요한 직책을 맡았다. 1759년에 도승지, 1763년에 지경연사, 1765년에 강화유수 등을 역임했다. 그는 은퇴 후 형제들과 함께 기로소에 들어가 일종의 명예직인 봉조하가 되었고, 이는 그의 삶이 복문, 즉 행운의 문이라고 일컬어진 것을 의미한다.\n" +
                        "\n" +
                        "김상익은 헌납을 맡았을 때, 궁중의 부조리를 용감하게 고발해 왕의 비난을 받았지만, 그의 청렴함과 충심을 보여주는 계기가 되며 많은 존경을 받았다. 이것은 그가 진정한 국가 기능을 수행하고자 하는 신하라는 것을 보여주며, 그의 생애는 긍정적인 정치 활동과 우수한 공적을 달성하기 위한 노력으로 가득 차있다.\n");



                break;
            }

            case "권응수(權應銖)": {

                lifesummary.setContents("권응수는 조선시대 뛰어난 무신과 의병장으로, 별시무과에 급제하여 여러 고을의 의병장을 이끌며 고향을 지키는데 주력하였다. 임진왜란이 일어났을 때, 권응수는 의병을 모집하여 활발히 활동하였고, 영천성을 화공으로 대승해 수복하는 등 여러 전과를 올렸다. \n" +
                        "\n" +
                        "그는 안동의 모은루에서 적을 대파하고, 밀양의 적을 격파하며 계속해서 전공을 세웠다. 그의 공로로 여러 차례에 걸쳐 경상도병마좌별장, 충청도방어사, 경상도방어사 등의 높은 지위를 밟았다. 그리고 그는 병마절도사 김응서와 함께 정유재란 때 달성까지 추격하며 적을 물리친 기록이 있다.\n" +
                        "\n" +
                        "권응수는 그 시대의 전략과 지혜를 이용하여 민중을 이끌며 전쟁을 치르는 동안, 그의 탁월한 지휘력과 용감함을 앞세워 수많은 승리를 이끌어냈으며, 그의 공로는 선무공신 2등으로 기록되었다. 그는 이와 같은 여러가지 공로로 인해 시호는 충의(忠毅)로 추증되었다.\n");
                break;
            }

            case "이산해(李山海)": {


                lifesummary.setContents("이산해는 조선 중기의 중요한 관리로서 홍문관 정자, 사헌부집의, 영의정 등을 역임했다. 그의 행정 역량은 선조의 강력한 지배 아래에서 피어났다. \n" +
                        "\n" +
                        "그는 학문에 뛰어나서 어린 나이에 진사가 되었으며, 그 후 급속히 승진하였다. 그는 선조의 즉위년에 명나라 사신을 맞이하였고, 다수의 고위 직책을 맡았다. \n" +
                        "\n" +
                        "그는 특히 서인과의 정치적인 대립 속에서 핵심 역할을 하였다. 그는 영의정에 올라 서인들을 능가하였고, 그는 조선 정부의 핵심 인물이 되었다.\n" +
                        "\n" +
                        "그는 또한 문학과 예술에서도 뛰어난 재능을 보였다. 그는 선조조 문장팔가 중 한 사람으로 불렸고, 그의 서예실력이 뛰어나서 경복궁 대액의 글씨를 쓴 적도 있다.\n" +
                        "\n" +
                        " 이산해는 조선 중기의 주요한 정치적 인물로서 많은 역할을 하였다. 그는 국내외 정치, 그리고 문학과 예술에 이르기까지 그의 역량은 상당했다. 그는 조선 역사에서 중요한 위치를 차지하고 있다.\n");


                break;
            }

        }


        //^^ gpt 4개중 두번째, lifesummary 입력

//        mbti.setMbti("ispj");
//        mbti.setContents(azureApi.getMBTI(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
//                silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name));
        switch(name) {
            case "박세채(朴世采)": {
                mbti.setMbti("ISFJ");
                mbti.setContents("박세채의 기록을 통해 그의 성격 측면을 분석해 이해하려 한다.\n" +
                        "\n" +
                        "I: 박세채는 뛰어난 학자였으며 학문을 사랑하였다. 이는 그가 내향적인 성격(I: Introverted)을 가진 사람일 수 있다는 신호를 준다.\n\n" +
                        "S: 그는 파당적 대립을 극단적으로 경계했고, 복잡한 정치 현실에서 자신의 사상을 지키려 했다. 이는 그가 확고한 내적 가치관을 공유하며, 그를 기반으로 판단하였음을 나타낸다.(S: Sensing)\n\n" +
                        "F: 박세채는 지속적으로 도덕적인 행동을 강조하였고, 이는 그가 F(Feeling)유형에 속했을 수 있다는 증거를 제공한다. 그의 접근 방식은 감정과 가치를 중요시하며, 행동을 결정하는데 인간적인 요소를 고려했다.\n\n" +
                        "J: 그는 지속적으로 중요한 관직을 역임했고, 조정 운영에 참여하며 정치적 고난을 적절히 극복했다. 이러한 행동은 그가 합리적인 판단력을 가지고(J: Judging) 계획 및 조직 능력이 뛰어났다는 것을 보여준다.\n\n" +
                        "따라서 박세채의 MBTI 유형은 'ISFJ'로 추정된다. 이 유형은 굉장히 관찰적이며 세부에 집중하는 경향이 있다. 또한, 이들은 전통을 중시하고 사회 규칙과 책임을 매우 중요하게 생각한다. ");




                break;
            }

            case "김상익(金尙翼)": {
                mbti.setMbti("ESTJ");
                mbti.setContents("김상익의 기록을 통해 몇 가지 성격적 특성을 추측해 볼 수 있다.\n" +
                        "\n" +
                        "E: 그는 도승지, 지경연사, 강화유수 등 다양한 직책을 역임했다. 이는 그가 새로운 경험과 변경에 개방적이며, 다양한 환경에서 작업하는 능력을 가졌음을 나타낸다.\n\n" +
                        "S: 그는 자신의 경험을 바탕으로 청나라에 다녀오는 경험을 하면서 정확하고 철저하게 일처리를 했다.\n\n" +
                        "T: 그는 자신의 의견을 명확하게 표현하고 국상과 관련된 문제에 대해 열정적으로 논의했다. 이는 공정하고도 냉철한 판단력을 가진 이성적인 사람을 보여준다.\n\n" +
                        "J: 마지막으로 그는 각별한 존중을 받았다. 이는 그가 사람들의 존경을 누렸으며, 그의 리더십이 도덕적으로 적절했음을 나타낸다.\n" +
                        "\n" +
                        "이러한 특성을 종합해 보면, 김상익은 MBTI에서 E(Extraverted, 외향적), S(Sensing, 센싱), T(Thinking, 사고), J(Judging, 판단) 유형에 해당할 가능성이 있다. 예를 들어 ESTJ 유형은 '실천가' 혹은 '관리자'로 불리며, 사회적인 네트워크를 중요시하며, 사물을 현실적이고 명확하게 이해하고 그에 따른 결정을 내리며,질서 정연하고 체계적인 환경을 선호하는 경향이 있다. 이 특징들은 김상익이 보여주었던 행동과 태도와 일치하는 경향을 보인다.");




                break;
            }

            case "권응수(權應銖)": {
                mbti.setMbti("ESTJ");
                mbti.setContents(" 권응수의 기록들을 통해 그가 가진 성격에 대해 몇 가지 추측을 할 수 있다.\n" +
                        "\n" +
                        "E: 그는 여러 곳에서 고위 직위를 역임하면서 결단력과 능력을 보여주었다. 적과의 전투에서도 끊임없이 성공하고, 그의 개인적인 행동이 전투의 승리에 큰 역할을 한 것으로 나타낸다. \n\n" +
                        "S: 이는 그가 그의 경험을 중시하여 정확하게 임무를 수행했다는 것을 나타낸다.\n\n" +
                        "T: 그는 지속적으로 성과를 내고 효과적인 전략을 실행에 옮기는 데 성공했다. 이는 그가 결정적이고 실용적으로 사고하고 행동하며 임무를 완수하는 데 집중하는 성향을 가진 사람일 가능성을 보여준다.\n\n" +
                        "J: 전쟁 상황에서도 그는 팀을 이끌고 전략을 세워 보여준 것으로 볼 때, 그는 리더십 능력과 뛰어난 판단력을 보유했을 가능성이 있다.\n" +
                        "\n" +
                        "이러한 특성을 감안할 때, 권응수는 MBTI에서 E(외향적), S(감각적), T(사고형), J(판단형)에 가깝다고 볼 수 있다. 그러므로 그의 추정 MBTI 유형은 ESTJ 일 것이다. '관리자'로 불리는 이 유형의 사람들은 방향성이 있으며, 체계적이고 사회적으로 책임감을 가지고 있으며, 훌륭한 관리 능력을 가진 것으로 잘 알려져 있다. 이는 권응수가 보여준 행동과 잘 일치한다.");




                break;
            }

            case "이산해(李山海)": {
                mbti.setMbti("ENTJ");
                mbti.setContents("이산해의 기록을 통해 그의 성격에 대해 추측 할 수 있다.\n\n"+
                        "E: 이산해는 관직을 거듭 올라가며 많은 직책을 역임하였으며 또한 선조와 명종의 신뢰를 받았다. 그의 정치적 경력에서 느낄 수 있는 그의 결단력과 냉철함, 그리고 유연성은 자신의 관점을 분명하게 표현하고 지키려는 의지를 보여준다.\n\n" +
                        "N: 그는 뛰어난 정치감각으로 고위 관직에 올랐으며 대부분의 정쟁에서 승리해왔다.\n\n" +
                        "T: 그는 폭넓은 경험을 통해 문제 해결에 필요한 기반 지식과 통찰력을 확보하고 문제를 객관적으로 사고해왔다. \n\n"+
                        "J: 또한, 그는 지식과 지혜를 이용해 많은 정치적 과제와 위기에 대처하였으므로 그의 의사 결정 프로세스는 실용적이며 결과 지향적이었다.\n" +
                        "\n" +
                        "이런 특성을 바탕으로 이산해의 MBTI 유형을 예측해보면 'ENTJ'(외향적, 직관적, 사고적, 판단적)로 볼 수 있다. ENTJ 유형은 '지도자' 혹은 '사령관'으로 불리며, 큰 그림을 보는 능력과 타인을 이끌어나가는 리더십 능력을 보유하고 있다. 이러한 유형은 이산해의 행동양식과 일치하는 경향이 있다.");




                break;
            }

        }

       // ^^ gpt 4개중 세번쨰, Mbti 입력

//        azureApi.getPrivateHistory(krpedia.getDefinition()+krpedia.getDescription()+krpedia.getMaintext()+
//                silokInfo.get(0).getContent() +silokInfo.get(1).getContent()+silokInfo.get(2).getContent(),name); 실제로 입력할떄


        switch(name) {
            case "박세채(朴世采)": {
                phistories[0].setEventyear(1649);
                phistories[0].setContents("진사가 되어 성균관에 입학");


                phistories[1].setEventyear(1659);
                phistories[1].setContents("천거로 익위사세마가 되었고, 자의대비의 복상문제에 대해 남인 계열의 대비복제설을 반대");


                phistories[2].setEventyear(1674);
                phistories[2].setContents("숙종이 즉위하고 자신은 유배생활을 했으며, 이 기간 동안 학문에 전념하여 \"독서기\"를 저술,");


                phistories[3].setEventyear(1680);
                phistories[3].setContents("경신대출척이라는 정권교체로 다시 등용되어 관직에 복귀");


                phistories[4].setEventyear(1689);
                phistories[4].setContents("기사환국 때에는 모든 관직에서 물러나 야인생활을 하며 학문에 몰두");


                phistories[5].setEventyear(1694);
                phistories[5].setContents("갑술옥사 이후에는 우의정· 좌의정을 두루 거치며 이른바 소론의 영도자가 되었음.");


                break;
            }

            case "김상익(金尙翼)": {

                phistories[0].setEventyear(1725);
                phistories[0].setContents("정시 문과에 병과로 급제");


                phistories[1].setEventyear(1732);
                phistories[1].setContents("충청도어사를 역임하면서 부자들이 농민의 쌀을 저렴하게 구매하고, 춘궁기에 비싸게 파는 관행을 지적하고 영조에게 알림");


                phistories[2].setEventyear(1735);
                phistories[2].setContents("이조좌랑에 임명되고, 임금에게 스스로의 행동을 조심히 하라고 간언함");


                phistories[3].setEventyear(1750);
                phistories[3].setContents("경기도관찰사와 대사헌을 지냄, 임금과 함께 기우제를 지냄");


                phistories[4].setEventyear(1757);
                phistories[4].setContents("사은부사로 청나라에 다녀옴");


                phistories[5].setEventyear(1765);
                phistories[5].setContents("강화유수에 임명되고 강화 어정포(漁汀浦)의 땅이 목장때문에 빈터로 버려져있으니 곡식을 심자고 주장");


                break;
            }

            case "권응수(權應銖)": {
                phistories[0].setEventyear(1583);
                phistories[0].setContents("별시무과에 급제");


                phistories[1].setEventyear(1592);
                phistories[1].setContents("임진왜란 발발시 의병을 모집하여 궐기, 7월에 의병대장이되고 12월에 좌도조방장으로 승진");


                phistories[2].setEventyear(1593);
                phistories[2].setContents("문경 당교에서 적을 대파하고, 산양탑전에서 100명의 목을 베는 등 전과를 올림");


                phistories[3].setEventyear(1595);
                phistories[3].setContents("경상좌도방어사를 겸하고, 4월에 형강에서 적을 대파");


                phistories[4].setEventyear(1603);
                phistories[4].setContents("충무위호군에 오르고 선무공신 2등으로 책록, 화산군에 봉해졌다.");


                phistories[5].setEventyear(1608);
                phistories[5].setContents("남영장을 겸하고 좌찬성에 추증되었다.");



                break;
            }

            case "이산해(李山海)": {
                phistories[0].setEventyear(1558);
                phistories[0].setContents("문과에 병과로 급제해 승문원에 등용되었다.");


                phistories[1].setEventyear(1561);
                phistories[1].setContents("글씨가 뛰어났고, 홍문관 정자가 되어 명종의 명을 받아 경복궁대액을 썼다.");


                phistories[2].setEventyear(1570);
                phistories[2].setContents("동부승지로 승진하였고, 선조의 명을 받아 구황적간어사(기근, 탐관오리에 관한 업무를 수행하는 어사)로 파견되었다.");


                phistories[3].setEventyear(1578);
                phistories[3].setContents("대사간이 되어 탐관오리를 탄핵해 파직시켰다.");


                phistories[4].setEventyear(1588);
                phistories[4].setContents("우의정에 올랐고, 동인이 남인, 북인으로 갈라지자 북인의 영수로 정권을 장악하였다.");


                phistories[5].setEventyear(1592);
                phistories[5].setContents("임진왜란때 선조를 호종해 개성에 이르렀다.");




                break;
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
