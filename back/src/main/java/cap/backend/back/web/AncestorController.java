package cap.backend.back.web;

import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.Person;
import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ancestor")
@RequiredArgsConstructor
@Slf4j
public class AncestorController {
    @GetMapping("/{id}")
    public ResponseEntity<?> ancestors(@PathVariable Long id, @RequestParam(defaultValue = "real") String type){
    //is in DB by id도 필요(404 띄우기 위해)
        //id에 따라 person객체 반환하는 서비스 필요
        Person a;

        //id에 따라 타임라인을 위한 (년도, 설명)리스트 반환하는 서비스 필요
        Privatehistory b;

        //Lifesummary.contents를 위한 설명(String type)반환하는 서비스 필요
        Lifesummary c;

        /* 조선사 주요 사건의 (년도, 이름) list 받환하는 서비스 필요(년도 오름차순 정렬)
        해당 id의 인물의 생 몰 년도를 이용해
        1. 생 o 몰 o: 생몰 이용해서 년도 제한하여 그 사이 사건 반환
        2. 생 o 몰 x: 생과 생 + 100사이 년도 제한하여 그 사이 사건 반환
        3. 생 x 몰 o: 몰 - 100과 몸 사이 년도 제한하여 그 사이 사건 반혼
        4. 생 x 몰 x: null 반환?
        */
        Oldevents d;

        //(예전관직, 현대관직) list를 반환하는 서비스 필요(예전 것이 왼쪽에 가깝게)
        Govsequence sequence;

        //현재 인물 사진 경로. 이건 서비스 만들 필요x person에서 뺴내면 된다
        String personPicPath;

        //------------------(가상)-------------------

        //가상 페이지 현대인물사진 경로 반환하는 서비스 필요;
        String imaginaryPicPath;

        //해당하는 id의 mbti 타입을 string으로 반환
        //이건 id로 mbti객체 반환하는 서비스만 필요. 컨텐츠 꺼내는 건 여기서 하면 될듯
        String mbti;
        String mbtiContent;


        if(type.equals("real")){

        }
        else if(type.equals("false")){

        }
        else{

        }

        return ResponseEntity.noContent().build();

    }


}
