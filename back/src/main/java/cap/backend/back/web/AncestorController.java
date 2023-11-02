package cap.backend.back.web;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.service.RealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ancestor")
@RequiredArgsConstructor
@Slf4j
public class AncestorController {


    private final RealService realservice;

    @GetMapping("/{id}")
    public EntityModel<SearchAncestorResponse> ancestors(@PathVariable Long id, @RequestParam(defaultValue = "real") String type){
        //id에 따라 person객체 반환하는 서비스 필요
        ClanId clan5=new ClanId();
        clan5.setClanHangul("해평");
        clan5.setSurnameHangul("윤");
        clan5.setSurnameHanja("海平");
        Clan clan_5=new Clan();
        clan_5.setClanid(clan5);
        clan_5.setCho('ㅇ');

        //id에 따라 person객체 반환하는 서비스 필요
        Person ancestor = new Person();
        ancestor.setId(7L);
        ancestor.setClan(clan_5);
        ancestor.setName("윤흔(尹昕)");
        ancestor.setPersonpicture("www.hello.com");
        ancestor.setBirthyear(1564);
        ancestor.setDeathyear(1638);
        ancestor.setTong(71);
        ancestor.setMu(88);
        ancestor.setJi(91);
        ancestor.setJung(99);
        ancestor.setMae(98);

        ancestor=realservice.findOne(7L); //매개변수로 넘어오는 id로 대체

        //id에 따라 타임라인을 위한 (년도, 설명)리스트 반환하는 서비스 필요
        Map<Integer, String> timeline = new HashMap<>();
        timeline.put(1566, "태어남");
        timeline.put(1588, "과거급제");
        timeline.put(1598, "병조판서에 오르다");
        timeline.put(1601, "좌의정 등극");
        timeline.put(1618, "영의정에 오르다");
        timeline.put(1638, "사망");

        timeline=realservice.findPrivateHistoriesById(7L); //매개변수로 넘어오는 id로 대체

        //Lifesummary.contents를 위한 설명(String type)반환하는 서비스 필요
        String lifeSummary = "윤흔(尹昕, 1564년 10월 4일 - 1638년 12월 17일)은 조선시대 후기의 무신, 정치인이다." +
                " 본관은 해평. 윤두수의 둘째 아들, 윤훤의 형이다. 본관은 해평(海平)으로" +
                " 초명은 양(暘)이고, 자는 시회(時晦), 호는 도재(陶齋) 또는 청강(晴江), 계음(溪陰)이다. 시호는 정민(靖敏)이다. 이괄의 난," +
                " 병자호란, 정묘호란 당시 인조를 수행하였으며, 병자, 정묘호란 당시 청나라와의 협상을 반대하며 주전론을 주장하였다.\n" +
                "\n" +
                "당색은 서인(西人)으로, 광해군 때 승정원우승지 등을 지냈으나 1613년(광해군 5년) 계축옥사 때," +
                " 첩의 남동생이 칠서지변의 서양갑(徐羊甲)이라서 연좌되어 파직되었다가," +
                " 1620년(광해군 12년) 관직에 임명되자 번번히 병을 이유로 관직을 사양하였다. 1623년(광해군 15년)" +
                " 인조반정 뒤에 관작이 복구되어 예조참판·중추부지사, 예조판서 등을 역임했다." +
                " 1624년 이괄의 난 때에는 인조를 공주까지 호종하였고, 왕을 공주로 호종한 공으로 가의대부에 오르고 예조참판을 지냈다.\n" +
                "\n" +
                "병자호란 때에는 후금과의 협상을 강력 반대하였다. 그 뒤 예조참판으로 재직 중에는 1632년 인목대비와 " +
                "1635년 인열왕후의 국상 장례를 주관하였으며 정묘호란 때에도 임금을 호종하였으며, 1636년(인조 14년) 정묘호란 때에도 남한산성으로" +
                " 인조를 수행, 김상헌과 함께 주전론을 주장하였다. 사후 병자, 정묘호란 때의 호종 공로로 증 의정부좌의정에 추증되었다." +
                " 윤웅렬(尹雄烈), 윤영렬(尹英烈) 형제는 그의 7대손, 윤치호는 그의 8대손이다. 이이·성혼·정철의 문인이다.";


        lifeSummary=realservice.findLifeSummaryById(id);

        /* 조선사 주요 사건의 (년도, 이름) list 받환하는 서비스 필요(년도 오름차순 정렬)
        해당 id의 인물의 생 몰 년도를 이용해
        1. 생 o 몰 o: 생몰 이용해서 년도 제한하여 그 사이 사건 반환
        2. 생 o 몰 x: 생과 생 + 100사이 년도 제한하여 그 사이 사건 반환
        3. 생 x 몰 o: 몰 - 100과 몸 사이 년도 제한하여 그 사이 사건 반혼
        4. 생 x 몰 x: null 반환?
        */
        Map<Integer, String> mainEvents = new HashMap<>();
        mainEvents.put(1567, "무오사화");
        mainEvents.put(1592, "임진왜란");
        mainEvents.put(1600, "병자호란");
        mainEvents.put(1607, "메롱");
        mainEvents.put(1620, "하이");

        mainEvents=realservice.findOldEventsById(7L); //매개변수로 넘어오는 id로 대체




        //(예전관직, 현대관직) list를 반환하는 서비스 필요(예전 것이 왼쪽에 가깝게)
        Map<Integer, String> govSequence = new HashMap<>();
//        govSequence.put("종 9품", "9급 공무원");
//        govSequence.put("종 7품", "7급 공무원");
//        govSequence.put("병조판서", "5급 공무원");
//        govSequence.put("좌의정", "국회의장");
//        govSequence.put("영의정", "국무총리");

        govSequence=realservice.findGovSequenceById(7L); //매개변수로 넘어오는 id로 대체



        //현재 인물 사진 경로. 이건 서비스 만들 필요x person에서 뺴내면 된다
        String personPicPath = ancestor.getPersonpicture();
        personPicPath=realservice.findPictureById(7L); //매개변수로 넘어오는 id로 대체


        //------------------(가상)-------------------

        //가상 페이지 현대인물사진 경로 반환하는 서비스 필요;
        String imaginaryPicPath = "www.imaginarypath.com";



        //해당하는 id의 mbti 타입을 string으로 반환
        //이건 id로 mbti객체 반환하는 서비스만 필요. 컨텐츠 꺼내는 건 여기서 하면 될듯
        Mbti mbti1 = new Mbti();
        mbti1.setMbti("ENTP");
        mbti1.setContents("윤흔은 이러이러이러이러한 이유로 어쩌구저쩌구" +
                "이유로 인하여 메롱메롱메롱 으로 인하여" +
                "어쩌구저쩌구 이유로 인해 ENTP에 제일 가까워 보입니다");
        String mbti = mbti1.getMbti();
        String mbtiContent = mbti1.getContents();

        SearchAncestorResponse searchAncestorResponse = new SearchAncestorResponse(ancestor, lifeSummary,
                timeline, mainEvents, govSequence, personPicPath, imaginaryPicPath, mbti, mbtiContent);


        return EntityModel.of(searchAncestorResponse, linkTo(methodOn(AncestorController.class).ancestors(id,"real")).withRel("real"),
                linkTo(methodOn(AncestorController.class).ancestors(id, "imaginary")).withRel("imaginary"));


    }


}
