package cap.backend.back.preprocessing.p_oldevents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class pre_oldevents {

    String path = "rawdata\\oldevents\\joseonEvents.csv";
    String csvSplitBy = ",";

    public List<List<String>> readCSV() {

        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;
        System.out.println(System.getProperty("user.dir")+"\\resources\\"+path);


        try {
            br = Files.newBufferedReader(Paths.get(System.getProperty("user.dir")+"\\resources\\"+path));
            String line = "";


            while ((line = br.readLine()) != null) {
                List<String> stringList = new ArrayList<>();
                String stringArray[] = line.split(csvSplitBy);
                System.out.println(stringArray);

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

        return list;
    }






}
