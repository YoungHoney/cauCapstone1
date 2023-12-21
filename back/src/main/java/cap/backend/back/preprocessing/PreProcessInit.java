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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class PreProcessInit {

   public PreProcessInit(ApplicationContext context) throws Exception {
       pre_oldevents task1=context.getBean(pre_oldevents.class);
       pre_oldgov task2=context.getBean(pre_oldgov.class);
       pre_moderngov task3=context.getBean(pre_moderngov.class);
       pre_govmatch task4=context.getBean(pre_govmatch.class);
       DemoSetting demo=context.getBean(DemoSetting.class);
       NewmanService newmanService=context.getBean(NewmanService.class);
       S3DownloadExample as=context.getBean(S3DownloadExample.class);
       S3UploadExample sa=context.getBean(S3UploadExample.class);



//       as.doDownload();





//

       task1.saveCSV(); // 조선시대 사건 세팅
       task2.saveCSV(); // 옛 관직 세팅
       task3.saveCSV(); //현대관직 세팅
       task4.doMatching(); //관직매칭해놓기


  //     String home_dir=System.getProperty("user.dir"); //..... cauCapstone1/back디렉토리
    //   sa.doUpload("바다.png",home_dir);


//
//
//
//
       //demo.doDemoSetting("박세채(朴世采)","朴","박","반남");
       //demo.doDemoSetting("김상익(金尙翼)","金","김","강릉");
       //demo.doDemoSetting("권응수(權應銖)","權","권","안동");
 
       //demo.doDemoSetting("이산해(李山海)","李","이","한산");
//
       //newmanService.doNewmanSetting("윤은로(尹殷老)");
       System.out.println("pre setting end");








    }
}
