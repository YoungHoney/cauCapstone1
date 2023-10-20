package cap.backend.back.web;

import cap.backend.back.domain.Clan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Getter @Setter
public class SearchClanResponse {
    List<EntityModel<String>> ancestors;
    List<EntityModel<Clan>> clans;
    public SearchClanResponse(List<EntityModel<String>> ancestors,List<EntityModel<Clan>> clans){
        this.ancestors = ancestors;
        this.clans = clans;
    }

}
