package cap.backend.back.web;


import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import cap.backend.back.repository.PersonRepository;
import cap.backend.back.domain.compositekey.ClanId;
import cap.backend.back.repository.PersonRepository;

import cap.backend.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public String searchByName(@RequestParam String name, RedirectAttributes redirectAttributes){

        /*db에 있는 경우
        return "redirect:/ancestor/{id}";
         */

        /* db에는 없고 민족에는 있는 경우
        //작업수행 후
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/ancestor/{id}";
         */

        /*db에도 없고 민족에도 없는 경우
        error page
         */
        /*
        아무 것도 안치고 검색창 검색 누르면?
         */
        log.info("name={}", name);
        return "hello";
    }
    @GetMapping("/initial/{letter}")
    public CollectionModel<EntityModel<Clan>> searchByInitial(@PathVariable char letter){
        ClanId clan1=new ClanId();
        clan1.setClanHangul("해평");
        clan1.setSurnameHangul("윤");
        clan1.setSurnameHanja("海平");



        ClanId clan2=new ClanId();
        clan2.setClanHangul("파평");
        clan2.setSurnameHangul("윤");
        clan2.setSurnameHanja("巴平");

        Clan clan_1=new Clan();
        clan_1.setClanid(clan1);
        clan_1.setCho('ㅇ');

        Clan clan_2=new Clan();
        clan_2.setClanid(clan2);
        clan_2.setCho('ㅇ');

        List<Clan> ls=new ArrayList<>();
        ls.add(clan_1);
        ls.add(clan_2);

        List<EntityModel<Clan>> clans=  ls.stream()
                .map(clan -> EntityModel.of(clan,
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter,
                                clan.getClanid().getClanHangul()+clan.getClanid().getSurnameHangul()+"씨")).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                        .collect(Collectors.toList());


        return CollectionModel.of(clans, linkTo(methodOn(SearchController.class).searchByInitial(letter)).withSelfRel());

    }

    @GetMapping("/initial/{letter}/{clan}")
    //clan optional value로 만들어서 합치는 것도 가능할 듯
    public EntityModel<SearchClanResponse> searchByInitialClan(@PathVariable char letter, @PathVariable String clan){
        String tempClan = null;
        if (clan.endsWith("씨")) {
            // 마지막 글자(씨)를 제외한 나머지 문자열을 추출
            tempClan = clan.substring(0, clan.length() - 1);
        } else {//오류 코드 나중에 구현해야하면 하면 될듯
        }
        Clan a = searchService.findClanByWholeName(tempClan);
        //밑의 ancestor가 string이라 List<EntityModel<Person>>이 아니라 string이다
        List<EntityModel<String>> ancestors = searchService.findPersonnamesByClan(a).stream().
                map(ancestor -> EntityModel.of(ancestor, //ancestor는 스트링 객체가 아니다
                        linkTo(methodOn(AncestorController.class).ancestors(searchService.findIdByName(ancestor), "real")).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withRel("clan")))
                        .collect(Collectors.toList());

        List<EntityModel<Clan>> clans = searchService.findClansByLetter(letter).stream()
                .map(clan1 -> EntityModel.of(clan1,
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                .collect(Collectors.toList());

        SearchClanResponse searchClanResponse = new SearchClanResponse(ancestors, clans);

        return EntityModel.of(searchClanResponse,
                linkTo(methodOn(SearchController.class).searchByInitialClan(letter, clan)).withSelfRel());

    }
}
