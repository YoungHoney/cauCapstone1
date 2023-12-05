package cap.backend.back.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class KrPediaApi {

    public String[] getKrpediaInfo(String name) throws IOException, ParseException {
        String addURL=getReviceURL(name); // E0001234
        String URL="https://suny.aks.ac.kr:5143/api/Article/"+addURL;

        //System.out.println(URL);

        JSONParser jParser=new JSONParser();
        JSONObject jsonObj=new JSONObject();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .addHeader("accessKey","ADE0FA5F-8B9B-40EA-9DF4-9991804D1CDB")
                .build();

        try(Response response=client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("응답오류, 내용 : "+response);
            }
            String responseBody=response.body().string();
            jsonObj=(JSONObject)jParser.parse(responseBody);


        }

        catch (IOException e){
            e.printStackTrace();

        }

        JSONObject articleObj=(JSONObject)jsonObj.get("article");



        String origin=(String) articleObj.get("origin");
        String def=(String) articleObj.get("definition");
        String body=(String)articleObj.get("body");
        JSONObject headMedia=(JSONObject)articleObj.get("headMedia");

        //이미지 검색
        String imageUrl=(String)headMedia.get("url");

        if(imageUrl==null) {
          imageUrl="shes'gone";
        }


        JSONArray arr4Year=(JSONArray)articleObj.get("attributes");
        JSONObject arr4Birth=(JSONObject)arr4Year.get(1);
        JSONObject arr4Death=(JSONObject)arr4Year.get(2);
        JSONObject arr4clan=(JSONObject)arr4Year.get(3);


        String birthYear=(String)arr4Birth.get("attrValue");

        if(birthYear.length()>4) {

            birthYear=birthYear.substring(0,4);
        }


        String deathYear=(String)arr4Death.get("attrValue");

        if(deathYear.length()>4) {

            deathYear=deathYear.substring(0,4);

        }

        String clan=(String)arr4clan.get("attrValue");




        //body 처리 로직

        String gaesol=getGaesol(body);
        gaesol=remove_article_thing(gaesol);

        body=after_sang_ae(body);

        body=remove_article_thing(body);
        body=moondan_moosi(body);




        String[] result=new String[8];

        result[0]=birthYear;
        result[1]=deathYear;
        result[2]=origin;
        result[3]=def;
        result[4]=gaesol;
        result[5]=body;
        result[6]=clan;
        result[7]=imageUrl;

        return result;

    }
    public String getReviceURL(String name) throws IOException, ParseException {
        String[] arr=name.split("\\(");
        name=arr[1];
        name=name.substring(0,name.length()-1); //한자이름만 추출

        OkHttpClient client=new OkHttpClient();
        JSONParser jParser=new JSONParser();
        JSONObject jsonObj=new JSONObject();

        String url_1="https://suny.aks.ac.kr:5143/api/Article/Search/"+name+"?page=1&type=인물"; //한글(한자) 형식에서 한자로 검색해야 제대로 나온다.


        Request request=new Request.Builder()
                .url(url_1)
                .addHeader("accessKey","ADE0FA5F-8B9B-40EA-9DF4-9991804D1CDB")
                .build();

        try(Response response=client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException("응답오류, 내용 : "+response);
            }
            String responseBody=response.body().string();
            


            jsonObj=(JSONObject)jParser.parse(responseBody);

        }

        catch (IOException e){
            e.printStackTrace();

        }

        JSONArray arrayObj= (JSONArray) jsonObj.get("articles");


        JSONObject articleObj=(JSONObject)arrayObj.get(0);

        String reviseURL=(String)articleObj.get("eid");
        return reviseURL;




    }

//    public String[] getNameInfo(String wholename) { //홍길동(홍
//
//    }

    private String getGaesol(String body) {
        //  String keyword="# 생애 및 활동사항";
        String result="";
        String[] temp=new String[2];
        temp=body.split("\r\n\r\n|\n\r\n",2);
        result=temp[0];
        temp=result.split("# 개설\r\n|# 가계 및 인적 사항",2);
        result=temp[1];

        System.out.println("temp[0] = " + temp[0]);
        System.out.println("temp[1] = " + temp[1]);

        return result;
    }

    private String moondan_moosi(String body) {
        String result=body.replaceAll("\\r|\\n","");


        return result;
    }

    private String remove_article_thing(String body) {

        String result=body.replaceAll("\\(/Article/E[^)]+\\)|\\[\\^(\\d+)\\]|\\[|\\]","");

        return result;
    }

    private String after_sang_ae(String body) {
        String keyword="# 생애 및 활동사항";
        String result="";
        String[] temp=new String[2];
        temp=body.split("# 생애 및 활동사항|# 주요활동|# 주요 활동",2);
        result=temp[1];



        System.out.println("sdf temp[0] = " + temp[0]);
        System.out.println("sdf temp[1] = " + temp[1]);

        return result;
    }

}
