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

        Person a;

        //private history(타임라인)의 설명필요
        //service로 (넌도, 설명) list 받아야한다(map으로 넘겨주기)
        Privatehistory b;

        //Lifesummary.contents
        Lifesummary c;

        //(년도, 이름) list 받아야한다(년도 오름차순 정렬)
        Oldevents d;
        
        //(예전관직, 현대관직) list 받아야한다(예전게 왼쪽에 가깝게)
        List<Govsequence> sequence;
        //------------------(가상)-------------------
        //해당하는 id의 mbti content string으로 반환
        Mbti e;
        //이미지 파일 경로. 서비스에서 imagePath를 받는다
        String imagePath;


        if(type.equals("real")){

        }
        else if(type.equals("false")){

        }
        else{

        }

        return ResponseEntity.noContent().build();

    }


}
