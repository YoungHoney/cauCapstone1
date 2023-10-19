package cap.backend.back.web;

import cap.backend.back.domain.Clan;
import cap.backend.back.repository.PersonRepository;
import cap.backend.back.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public CollectionModel<EntityModel<Clan>> searchByInitial(@PathVariable String letter){
        List<EntityModel<Clan>> clans = searchService.findClansByLetter(letter).stream()
                .map(clan -> EntityModel.of(clan,
                        linkTo(methodOn(SearchController.class).searchByInitialClan(letter,
                                clan.getClanid().getClanHangul()+clan.getClanid().getSurnameHangul()+"씨")).withSelfRel(),
                        linkTo(methodOn(SearchController.class).searchByInitial(letter)).withRel("initial")))
                        .toList();
        return CollectionModel.of(clans, linkTo(methodOn(SearchController.class).searchByInitial(letter)).withSelfRel());

    }
    @GetMapping("/initial/{letter}/{clan}")
    //clan optional value로 만들어서 합치는 것도 가능할 듯
    public String searchByInitialClan(@PathVariable String letter, @PathVariable String clan){
        /*
        해당하는 초성으로 시작하는 본관 목록 들고오기
         */
        log.info("letter={}, clan={}", letter, clan);
        return "hello";
    }
}
