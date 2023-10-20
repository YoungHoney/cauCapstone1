package cap.backend.back.preprocessing;

import cap.backend.back.preprocessing.p_oldevents.pre_oldevents;
import cap.backend.back.preprocessing.p_oldevents.pre_oldgov;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreProcessInit {

   public PreProcessInit(ApplicationContext context){
       pre_oldevents task1=context.getBean(pre_oldevents.class);
       pre_oldgov task2=context.getBean(pre_oldgov.class);


      // task1.saveCSV();
      // task2.saveCSV();

    }
}
