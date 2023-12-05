package cap.backend.back.api;

import cap.backend.back.domain.dto.SilokDocument;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
public class SilLokApi {
    // 곽영헌(郭永憲)

    public List<SilokDocument> SilokExtractor(String keyword) throws IOException, NoSuchAlgorithmException,
            KeyManagementException, IndexOutOfBoundsException  {

        List<SilokDocument> silokDocuments = extractSilokDocument(keyword);
        Iterator<SilokDocument> iterator = silokDocuments.iterator();

        while (iterator.hasNext()) {
            SilokDocument silokDocument = iterator.next();

            Document tempDocument = makeRequestToSilok(silokDocument.getDci());
            if (tempDocument == null) {
                // tempDocument가 null인 경우 해당 silokDocument를 리스트에서 제거
              //  System.out.println("tempDocument가 null이 너서 목록에서 삭제했습니다\n");
                iterator.remove();
            } else {
                try {
                    silokDocument.setContent(HtmlTextExtractor(tempDocument));
                    System.out.println(silokDocument.getContent());
                }catch (IndexOutOfBoundsException e){
                    iterator.remove();
                 //   System.out.println("silokDDocument 형식이 잘못되어 목록에 추가하지 않았습니다");
                }
            }

        }
        return silokDocuments;
    }
    private String dciToUrl(String input) throws IndexOutOfBoundsException{
        try {
            String[] parts = input.split("_");

            String output1 = "k" + parts[2].substring(0, parts[2].length() - 1)
                    .toLowerCase() + "a";
            String output2 = ((parts[3].charAt(0) - 'A') + 1)
                    + parts[3].substring(1)
                    + parts[4].substring(0, parts[4].length() - 1);
            String output3 = (parts[4].charAt(2) - 'A')
                    + parts[5].substring(0, parts[5].length() - 1);
            String output4 = parts[6].substring(1, parts[6].length() - 1);

            // Concatenating the outputs
            return "https://sillok.history.go.kr/id/"
                    + output1 + "_" + output2 + output3 + "_" + output4;
        }catch (IndexOutOfBoundsException e){
            return "dummy";
        }
    }
    private  Document makeRequestToSilok(String dci) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        // Trust all certs
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
        }

        // Now you can access an https URL without having the certificate in the truststore
        Document doc = null;
        try {
            String URL = dciToUrl(dci);
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return doc;
    }
    private String HtmlTextExtractor(Document doc) throws IndexOutOfBoundsException {
        // Find the first <hr class="ins_view_line"> element
try {
    Element element = doc.select("div.ins_view").get(0);
    // System.out.println("element = " + element);
    StringBuilder extractedText = new StringBuilder();

    // Check if the element exists and process it further
    if (element != null) {
        // Select all paragraph elements
        Elements paragraphs = element.select("p.paragraph");

        for (Element paragraph : paragraphs) {
            extractedText.append(paragraph.text());
            //.append("\n")하면 문단띄어씌기 함
        }
    }
    return extractedText.toString();
} catch (IndexOutOfBoundsException e){
    throw e;
}
    }

    public List<SilokDocument> extractSilokDocument(String keyword) {
        List<SilokDocument> silokDocuments = new ArrayList<>();
      //  System.out.println("SilLokApi.extractSilokDocument 시작 ");

        try {
            Document document = makeRequestToGoJongDB(keyword);
            //System.out.println("SilLokApi.extractSilokDocument   try 내부에서 makeRequestToGoJongDB 실행완료");

            // Select all <doc> elements
            Elements docElements = document.select("response > result > doc");

           // System.out.println("SilLokApi.extractSilokDocument Elements에 대한 document.select 다음");

            // Randomly select 3 documents
            int totalDocs = docElements.size();
            int[] randomIndices;


            if(totalDocs<=3) {
                randomIndices =new int[totalDocs];
                for(int i=0;i<totalDocs;i++) {
                    randomIndices[i]=i;
                }

            } else {
                randomIndices = getRandomIndices(totalDocs, 3);
            }





           // System.out.println("SilLokApi.extractSilokDocument try문 안쪽 ");
            for (int index : randomIndices) {
            //    System.out.println("SilLokApi.extractSilokDocument 안쪽");
                Element docElement = docElements.get(index);
                if(!docElement.select("field[name=DCI_s]").text().substring(11, 12).equals("C")) {
                    String dci = docElement.select("field[name=DCI_s]").text();
                    String publicationYear = docElement.select("field[name=편년서기년]").text();
                    String articleName = docElement.select("field[name=기사명]").text();
                    SilokDocument silokDocument = new SilokDocument(dci, publicationYear, articleName);
                    silokDocuments.add(silokDocument);
                }
            }
            //customdocument개수 0이면 결과 없음. 나중에 추가


        } catch (Exception e) {
            e.printStackTrace();
            return silokDocuments;
        }
        return silokDocuments;
    }

    private int[] getRandomIndices(int maxRange, int count) {
        if (count > maxRange) {
            count = maxRange;
        }
        Random rd=new Random(1557L);
        int[] indices = new int[count];
        for (int i = 0; i < count; i++) {
            int randomIndex;
            do {
//                double temp=Math.random();
//                //randomIndex = (int) (temp * maxRange);


                randomIndex=(int)(rd.nextDouble()*maxRange);



            } while (contains(indices, randomIndex));
            indices[i] = randomIndex;
        }
        return indices;
    }
    // Check if an array contains a specific value
    private boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }
    private Document makeRequestToGoJongDB(String keyword) throws IOException {
        try {//keyword = 검색어 ex)윤흔  row = 검색 갯수
            String apiUrl = "http://db.itkc.or.kr/openapi/search?secId=JT_BD&keyword="
                    + keyword + "&start=0&rows=300";


            // Parse the HTML response using Jsoup
            Document doc = Jsoup.connect(apiUrl).get();

            return doc;
        }catch (Exception e){
            System.out.println("Illegal charcter in query!!");
            throw e;
        }
    }
}
