package cap.backend.back.web;


import cap.backend.back.domain.Clan;

import cap.backend.back.domain.dto.SearchClanResponseDTO;
import cap.backend.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> searchByName(@RequestParam String name){
        if(searchService.isPersoninDBByName(name)) {
            // Ancestor found in the database
            // 나중에 동명이인 처리하려면 서비스 확장해서 두명이상이면 다른 페이지 리다이렉트하게 하면 될 듯
            return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/ancestor/"
                    + searchService.findIdByName(name)).build();
        }
        //ancestor not in DB
        /* db에는 없고 민족에는 있는 경우
        민족에서 정보 가져와 db 저장
        return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/people/" + newId).build();
         */
        //db에도 없고 민족에도 없는 경우
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, we don’t have any information about the person.");
    }
    @GetMapping("/initial/{letter}")
    public CollectionModel<EntityModel<Clan>> searchByInitial(@PathVariable char letter){

        List<EntityModel<Clan>> clans=  searchService.findClansByLetter(letter).stream()
                .map(clan -> EntityModel.of(clan,
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter,
                                clan.getClanid().getClanHangul()+clan.getClanid().getSurnameHangul()+"씨")).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                        .collect(Collectors.toList());

        return CollectionModel.of(clans, linkTo(methodOn(SearchController.class).searchByInitial(letter)).withSelfRel());
    }

    @GetMapping("/initial/{letter}/{clan}")
    //clan optional value로 만들어서 합치는 것도 가능할 듯
    public EntityModel<SearchClanResponseDTO> searchByInitialClan(@PathVariable char letter, @PathVariable String clan){
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
                            linkTo(methodOn(AncestorController.class).ancestors(searchService.findIdByName(ancestor), "real")).withSelfRel(),
                            linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withRel("clan"));
                })
                .collect(Collectors.toList());


        //본관과 그에 해당하는 링크를 모아 반환
        List<EntityModel<Clan>> clans = searchService.findClansByLetter(letter).stream()
                .map(clan1 -> EntityModel.of(clan1,
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                .collect(Collectors.toList());

        //map 사용?
        SearchClanResponseDTO searchClanResponse = new SearchClanResponseDTO(ancestors, clans);

        return EntityModel.of(searchClanResponse,
                linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withSelfRel());



    }
}
