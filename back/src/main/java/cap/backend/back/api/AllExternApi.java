package cap.backend.back.api;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AllExternApi {

    private final AzureApi azureApi;
    private final KrPediaApi krpediaApi;
    private final OpenAiApi openAiApi;
    private final SilLokApi silLokApi;

    public void doAllThing() throws IOException, ParseException {
        long stime=System.currentTimeMillis();


       // krpediaApi.MinSajeon("박세채(朴世采)");
       // openAiApi.OpenAItest();


        long etime=System.currentTimeMillis();

        System.out.println("실행시간 = "+(etime=stime));


    }







   
 



   
}