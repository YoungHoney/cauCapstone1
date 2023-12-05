package cap.backend.back.preprocessing.p_oldevents;

import cap.backend.back.domain.Oldevents;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.repository.GovRepository;
import cap.backend.back.repository.OldEventsRepository;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class pre_oldgov {



    private final GovRepository govRepository;

    String csvSplitBy = ",";


    @Transactional
    public void saveCSV() {

        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;


        try {
            InputStream is=getClass().getResourceAsStream("/rawdata/oldgov/oldgovlist.csv");
            br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));

            String line = "";


            while ((line = br.readLine()) != null) {
                List<String> stringList = new ArrayList<>();
                String stringArray[] = line.split(csvSplitBy);

                //String temps=stringArray[0].substring(0,4);
                //System.out.println(stringArray[0]+"   "+stringArray[1]+"   "+stringArray[2]);

                Oldgov temp=new Oldgov();
                temp.setName(stringArray[0].split("\\(")[0]);
                temp.setRank(stringArray[2]);
                temp.setGovmatches(null);

                if(stringArray[1].equals("문관")) {
                    temp.setIswarrior(false);
                } else {
                    temp.setIswarrior(true);
                }


                if(govRepository.findOldgov(temp.getName())==null) {
                    govRepository.save(temp);
                }




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
