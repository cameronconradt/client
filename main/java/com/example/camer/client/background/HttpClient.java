package com.example.camer.client.background;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;

/**
 * Created by camer on 3/27/2018.
 */

public class HttpClient {

    private String port;
    private String ip;

    public HttpClient(String port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public String getUrl(String urlString, String requestBody, String authToken){
        try{
            URL url = new URL("http://" + ip + ":" + port + urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            if(authToken != null){
                connection.setRequestProperty("Authorization", authToken);
            }
            connection.connect();

            if(requestBody != null){
                Writer writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.close();
            }
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = connection.getInputStream();
                return getStringfromInputStream(responseBody);
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private String getStringfromInputStream(InputStream is){
        BufferedReader br;
        StringBuffer sb = new StringBuffer();

        String temp;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
