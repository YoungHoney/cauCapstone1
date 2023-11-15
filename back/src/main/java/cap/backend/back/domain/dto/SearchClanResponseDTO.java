package cap.backend.back.domain.dto;

import cap.backend.back.domain.Clan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class SearchClanResponseDTO {
    List<EntityModel<Map<String, Object>>> ancestors;
    List<EntityModel<Clan>> clans;
    public SearchClanResponseDTO(List<EntityModel<Map<String, Object>>> ancestors, List<EntityModel<Clan>> clans){
        this.ancestors = ancestors;
        this.clans = clans;
    }

}
