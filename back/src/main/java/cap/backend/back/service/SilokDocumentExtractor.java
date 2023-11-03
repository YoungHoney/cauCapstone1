package cap.backend.back.service;

import cap.backend.back.domain.dto.SilokDocument;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SilokDocumentExtractor {
    public static List<SilokDocument> extractSilokDocument(String keyword) {
        List<SilokDocument> silokDocuments = new ArrayList<>();

        try {
            Document document = makeRequest(keyword);

            // Select all <doc> elements
            Elements docElements = document.select("response > result > doc");

            // Randomly select 3 documents
            int totalDocs = docElements.size();
            int[] randomIndices = getRandomIndices(totalDocs, 3);


            for (int index : randomIndices) {
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
        }
        return silokDocuments;
    }

    private static int[] getRandomIndices(int maxRange, int count) {
        if (count > maxRange) {
            count = maxRange;
        }
        int[] indices = new int[count];
        for (int i = 0; i < count; i++) {
            int randomIndex;
            do {
                randomIndex = (int) (Math.random() * maxRange);
            } while (contains(indices, randomIndex));
            indices[i] = randomIndex;
        }
        return indices;
    }
    // Check if an array contains a specific value
    private static boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }
    private static Document makeRequest(String keyword) throws IOException {
        //keyword = 검색어 ex)윤흔  row = 검색 갯수
        String apiUrl = "http://db.itkc.or.kr/openapi/search?secId=JT_BD&keyword="
                + keyword + "&start=0&rows=300";

        // Parse the HTML response using Jsoup
        Document doc = Jsoup.connect (apiUrl).get();

        return doc;
    }

}
