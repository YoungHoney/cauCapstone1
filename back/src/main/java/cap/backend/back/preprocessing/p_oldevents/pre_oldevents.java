package cap.backend.back.preprocessing.p_oldevents;

import cap.backend.back.domain.Oldevents;
import cap.backend.back.repository.OldEventsRepository;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class pre_oldevents {



    private final OldEventsRepository oldeventsrepository;
   // String path = "rawdata\\oldevents\\joseonEvents.csv";
    String csvSplitBy = ",";


    @Transactional
    public void saveCSV() {

        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;


        try {
            InputStream is=getClass().getResourceAsStream("/rawdata/oldevents/joseonEvents.csv");
            br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
            String line = "";


            while ((line = br.readLine()) != null) {
                List<String> stringList = new ArrayList<>();
                String stringArray[] = line.split(csvSplitBy);
                
                String temps=stringArray[0].substring(0,4);


                Oldevents temp=new Oldevents();
                temp.setYear(Integer.parseInt(temps));
                temp.setName(stringArray[1]);

                oldeventsrepository.save(temp);


                stringList = Arrays.asList(stringArray);
                list.add(stringList);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                assert br != null;
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }






}
