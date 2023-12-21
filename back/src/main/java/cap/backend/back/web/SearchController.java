package cap.backend.back.web;


import cap.backend.back.domain.Clan;

import cap.backend.back.service.NewmanService;
import cap.backend.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;
    private final NewmanService newmanService;

    @PostMapping("/{name}")
    public ResponseEntity<Map<String, Long>> searchByName(@PathVariable String name){
        Long id = -1L;
        Map<String, Long> responseMap = new HashMap<>();
        if(searchService.isPersoninDBByName(name)) {
            // Ancestor found in the database
            id = searchService.findIdByName(name);
            responseMap.put("id", id);

            return new ResponseEntity<>(responseMap, HttpStatus.OK);
            // 나중에 동명이인 처리하려면 서비스 확장해서 두명이상이면 다른 페이지 리다이렉트하게 하면 될 듯
            /*
            return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/ancestor/"
                    + searchService.findIdByName(name)).build();

             */
        }
        //ancestor not in DB
        try {
            id=newmanService.doNewmanSetting(name);
        } catch(Exception e) {
            System.out.println(name);
            System.out.println("의 조상 정보 가져오는 동안 오류가 발생했습니다\n");
            e.printStackTrace();
            responseMap.put("id", id);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        responseMap.put("id", id);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
    @GetMapping("/initial/{letter}")
    public CollectionModel<EntityModel<Clan>> searchByInitial(@PathVariable char letter){

        List<EntityModel<Clan>> clans = searchService.findClansByLetter(letter).stream()
                .map(clan -> EntityModel.of(clan,
                        linkTo(methodOn(SearchController.class).searchByClan(clan.getClanid()
                                .getClanHangul()+clan.getClanid().getSurnameHangul()+"씨")).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                        .collect(Collectors.toList());

        return CollectionModel.of(clans, linkTo(methodOn(SearchController.class).searchByInitial(letter)).withSelfRel());
    }

    @GetMapping("/clan/{clan}")
    //clan optional value로 만들어서 합치는 것도 가능할 듯
    public CollectionModel<?> searchByClan(@PathVariable String clan){
        String tempClan = null;
        if (clan.endsWith("씨")) {
            // 마지막 글자(씨)를 제외한 나머지 문자열을 추출
            tempClan = clan.substring(0, clan.length() - 1);
        } else {//오류 코드 나중에 구현해야하면 하면 될듯
        }
        //tempClan은 해평윤 string. 해평윤 가지고 해평윤씨 type을 db에 꺼냄
        Clan a = searchService.findClanByWholeName(tempClan);

        //ancestor 이름과 그에 해당하는 결과 링크를 모아 반환
        List<EntityModel<Map<String, Object>>> ancestors = searchService.findPersonnamesByClan(a).stream()
                .map(ancestor -> {
                    Map<String, Object> ancestorMap = new HashMap<>();
                    ancestorMap.put("name", ancestor); // Assign a field name "name" to the payload
                    return EntityModel.of(
                            ancestorMap,
                            linkTo(methodOn(AncestorController.class).ancestors(searchService.findIdByName(ancestor), "real")).withSelfRel());
                }).toList();


        return CollectionModel.of(ancestors,
                linkTo(methodOn(SearchController.class).searchByClan(clan)).withSelfRel());

    }
}
