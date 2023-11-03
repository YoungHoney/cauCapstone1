package cap.backend.back.service;

import cap.backend.back.domain.dto.SilokDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class KeywordToSilok {
    public static List<SilokDocument> SilokExtractor(String keyword) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        List<SilokDocument> silokDocuments = SilokDocumentExtractor.extractSilokDocument(keyword);
        for(SilokDocument silokDocument:silokDocuments){
            silokDocument.setContent(HtmlTextExtractor(makeRequest(silokDocument.getDci())));
        }
        return silokDocuments;
    }
    private static String dciToUrl(String input) {
        String[] parts = input.split("_");

        String output1 = "k" + parts[2].substring(0, parts[2].length() - 1)
                .toLowerCase() + "a";
        String output2 = ((parts[3].charAt(0) - 'A')  + 1)
                + parts[3].substring(1)
                + parts[4].substring(0,parts[4].length() - 1);
        String output3 = (parts[4].charAt(2) - 'A')
                + parts[5].substring(0,parts[5].length() - 1);
        String output4 = parts[6].substring(1,parts[6].length() - 1);

        // Concatenating the outputs
        return "https://sillok.history.go.kr/id/"
                + output1 + "_" + output2 + output3 + "_" + output4;
    }
    private static Document makeRequest(String dci) throws IOException, NoSuchAlgorithmException, KeyManagementException {
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
        }
        return doc;
    }
    private static String HtmlTextExtractor(Document doc){
        // Find the first <hr class="ins_view_line"> element

        Element element = doc.select("div.ins_view").get(0);
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
    }
}

