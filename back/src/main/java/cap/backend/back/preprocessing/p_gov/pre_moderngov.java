package cap.backend.back.preprocessing.p_gov;

import cap.backend.back.domain.govrank.Moderngov;
import cap.backend.back.repository.GovRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class pre_moderngov {



    private final GovRepository govRepository;

    String csvSplitBy = ",";


    @Transactional
    public void saveCSV() {

        Moderngov temp1=new Moderngov();
        temp1.setName("현대미상");
        temp1.setPersonname("현대미상");
        temp1.setRank("9급");
        temp1.setIswarrior(false);
        temp1.setPersonpicture("/대한민국 행정부.png");


        BufferedReader br = null;


        try {
            InputStream is=getClass().getResourceAsStream("/rawdata/moderngov/moderngovlist.csv");
            br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));

            String line;


            while ((line = br.readLine()) != null) {
                String[] stringArray = line.split(csvSplitBy);




                Moderngov temp=new Moderngov();
                temp.setName(stringArray[0]);
                temp.setPersonname(stringArray[1]);
                temp.setRank(stringArray[2]);

                temp.setIswarrior(stringArray[3].equals("무관"));

                if(stringArray[1].contains("대한민국 행정부")) {
                    temp.setPersonpicture("/대한민국 행정부.png");
                } else {
                    String url="/"+stringArray[1]+".png";
                    temp.setPersonpicture(url);
                }

                govRepository.save(temp);



            } //while문

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
