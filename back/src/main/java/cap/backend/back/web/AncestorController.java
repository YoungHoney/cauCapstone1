package cap.backend.back.web;

import cap.backend.back.domain.Person;
import cap.backend.back.domain.dto.SearchAncestorResponseDTO;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.service.RealService;
import cap.backend.back.service.SearchService;
import cap.backend.back.service.VirtualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/ancestor")
@RequiredArgsConstructor
@Slf4j
public class AncestorController {


    private final RealService realservice;
    private final SearchService searchService;
    private final VirtualService virtualService;

    @GetMapping("/{id}")
    public EntityModel<SearchAncestorResponseDTO> ancestors(@PathVariable Long id, @RequestParam(defaultValue = "real") String type){


        //id에 따라 person객체 반환하는 서비스 필요
        Person ancestor = realservice.findOne(id);

        //조상의 definition
        String definition=realservice.findDefById(id);



        //id에 따라 타임라인을 위한 (년도, 설명)리스트 반환하는 서비스 필요
        Map<Integer, String> timeline = realservice.findPrivateHistoriesById(id);

        //Lifesummary.contents를 위한 설명(String type)반환하는 서비스 필요
        String lifeSummary = realservice.findLifeSummaryById(id);


        /* 조선사 주요 사건의 (년도, 이름) list 받환하는 서비스 필요(년도 오름차순 정렬)
        해당 id의 인물의 생 몰 년도를 이용해
        1. 생 o 몰 o: 생몰 이용해서 년도 제한하여 그 사이 사건 반환
        2. 생 o 몰 x: 생과 생 + 100사이 년도 제한하여 그 사이 사건 반환
        3. 생 x 몰 o: 몰 - 100과 몸 사이 년도 제한하여 그 사이 사건 반혼
        4. 생 x 몰 x: null 반환?
        */

        Map<Integer, String> mainEvents = realservice.findOldEventsById(id);



        //(예전관직, 현대관직) list를 반환하는 서비스 필요(예전 것이 왼쪽에 가깝게)
        Map<Integer, String> govSequence =realservice.findGovSequenceById(id);


        //현재 인물 사진 경로. 이건 서비스 만들 필요x person에서 뺴내면 된다
        String personPicPath = ancestor.getPersonpicture();



        //------------------(가상)-------------------

        //가상 페이지 현대인물사진 경로 반환하는 서비스 필요;
        String imaginaryPicPath = "www.imaginarypath.com";


        //가상페이지에서 조상에 대응되는 현대인물 이름[0]과 관직명[1]
        String[] modernPersonandGov= virtualService.findMatchBetweenAncestorAndModern(id);




        //해당하는 id의 mbti 타입을 string으로 반환
        //이건 id로 mbti객체 반환하는 서비스만 필요. 컨텐츠 꺼내는 건 여기서 하면 될듯
        Mbti mbti1 = virtualService.getMbtiById(id);
        String mbti = mbti1.getMbti();
        String mbtiContent = mbti1.getContents();

        SearchAncestorResponseDTO searchAncestorResponse = new SearchAncestorResponseDTO(ancestor,definition, lifeSummary,
                timeline, mainEvents, govSequence, personPicPath, imaginaryPicPath,modernPersonandGov, mbti, mbtiContent);


        return EntityModel.of(searchAncestorResponse, linkTo(methodOn(AncestorController.class).ancestors(id,"real")).withRel("real"),
                linkTo(methodOn(AncestorController.class).ancestors(id, "imaginary")).withRel("imaginary"));


    }
    @GetMapping("/name/{name}")
    public ResponseEntity<Map<String, Long>> findID(@PathVariable String name){
        Long id = null;
        try{
            id = searchService.findIdByName(name);
        }
        catch (Exception ignored){

        }
        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("id", id);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

}
