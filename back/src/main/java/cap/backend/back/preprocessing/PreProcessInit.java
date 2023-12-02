package cap.backend.back.preprocessing;

import cap.backend.back.api.OpenAiApi;

import cap.backend.back.preprocessing.p_gov.pre_govmatch;
import cap.backend.back.preprocessing.p_gov.pre_moderngov;
import cap.backend.back.preprocessing.p_oldevents.pre_oldevents;
import cap.backend.back.preprocessing.p_oldevents.pre_oldgov;
import cap.backend.back.service.NewmanService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class PreProcessInit {

   public PreProcessInit(ApplicationContext context) throws IOException, ParseException, NoSuchAlgorithmException, KeyManagementException {
       pre_oldevents task1=context.getBean(pre_oldevents.class);
       pre_oldgov task2=context.getBean(pre_oldgov.class);
       pre_moderngov task3=context.getBean(pre_moderngov.class);
       pre_govmatch task4=context.getBean(pre_govmatch.class);
       DemoSetting demo=context.getBean(DemoSetting.class);
       NewmanService newmanService=context.getBean(NewmanService.class);


//

       task1.saveCSV(); // 조선시대 사건 세팅
       task2.saveCSV(); // 옛 관직 세팅
       task3.saveCSV(); //현대관직 세팅
       task4.doMatching(); //관직매칭해놓기




       demo.doDemoSetting("박세채(朴世采)","朴","박","반남");
       demo.doDemoSetting("김상익(金尙翼)","金","김","강릉");
       demo.doDemoSetting("권응수(權應銖)","權","권","안동");
       demo.doDemoSetting("이산해(李山海)","李","이","한산");

       newmanService.doNewmanSetting("곽존중(郭存中)");
       System.out.println("pre setting end");








    }
}
