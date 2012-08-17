package poc.arch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        URL url = new URL("http://localhost:8080/VinDecoding/vins/JT3HN86R8Y0264981.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();
        InputStream in = null;

        //TODO proceed based on response code
        //Handle connection refused - system down, and timeout
        System.out.println("Http Response:" + conn.getResponseCode());
        if(conn.getResponseCode()!= HttpURLConnection.HTTP_OK){
            in = conn.getErrorStream();
        }else{
            in = conn.getInputStream();
        }

        StringBuilder vinsResponse = new StringBuilder();
        String contentType = conn.getContentType();
        System.out.println(contentType);
        if (contentType.equals("application/json")) {
            char[] buff = new char[1024];
            InputStreamReader isr = new InputStreamReader(in);
            int charsRead = 0;
            while ((charsRead = isr.read(buff)) != -1) {
                vinsResponse.append(buff, 0, charsRead);
            }
        }
        in.close();
        conn.disconnect();
        System.out.println("Response: " + vinsResponse.toString());
        getValues(vinsResponse.toString());
    }

    private static void getValues(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> vinInfo = mapper.readValue(json, new TypeReference<Map<String,Object>>() { });

        System.out.println(vinInfo.get("length").getClass());
        System.out.println(vinInfo.get("year").getClass());

    }

}
