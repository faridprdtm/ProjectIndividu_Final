package com.farid.projectindividu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {
    // constructor 1 :send POST REQUEST
    public String sendPostRequest(String requesstURL, HashMap<String, String> postDataParams) {
        // membuat URL
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requesstURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //mengirim req dari client ke server
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer= new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            //cek http status code untuk memastikan req diterima oleh server
            int responseCode= connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                // response dikirim dari server ke client
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                sb = new StringBuilder();
                String response;
                while ( (response= reader.readLine())!= null) {
                    sb.append(response);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(); //error message

        }
        return sb.toString();
    }
    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    //constructor 2: send GET Response -> GET_ALL dan GET_DETAIL
    public  String sendGetResponse (String responseURL){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null){
                sb.append(response +"\n");
            }
        }catch ( Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }

    // Get Detail
    public String sendGetResponse(String responseUrl, String id) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null) {
                sb.append(response + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}