package cap.backend.back.preprocessing.p_gov;


import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.repository.GovRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class pre_govmatch {

private final GovRepository govRepository;

public void doMatching() {

    Govmatch temp=new Govmatch();
    List<Oldgov> allList=govRepository.findAllOldgov();

    for(Oldgov o : allList) {
        temp.setOldgov(o);

    }


}





}
