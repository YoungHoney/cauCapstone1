package cap.backend.back.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
public class SilLokApi {

    public void GOJONGDB() throws IOException, NoSuchAlgorithmException, KeyManagementException {


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
        try {
            String URL = "https://sillok.history.go.kr/id/kma_11504024_001";
            Document doc = Jsoup.connect(URL).get();
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
