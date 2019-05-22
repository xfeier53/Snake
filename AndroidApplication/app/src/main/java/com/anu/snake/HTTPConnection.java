package com.anu.snake;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;

public class HTTPConnection {

    private static final String PORT = "8080";
    private static final String IP = "172.20.10.5";

    private static HttpURLConnection init(String address, String data) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // Time out
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // Set cache for Post
            conn.setUseCaches(false);
            // Get output stream
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            return conn;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String LoginByPost(String account, String password) {
        String result = "";
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/login";
        try {
            // Request
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            HttpURLConnection conn = init(address, data);
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                // Return result
                result = parseInfo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String RegisterByPost(String account, String password, String email) {
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/register";
        String result = "";
        try {
            // Request
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8");
            HttpURLConnection conn = init(address, data);
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                // Return result
                result = parseInfo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int GetBestScoreByPost(String account) {
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/getBestScore";
        String result = "";
        int score =  0;
        try {
            String data = "account=" + URLEncoder.encode(account, "UTF-8");
            HttpURLConnection conn = init(address, data);
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                result = parseInfo(is);
                score = Integer.valueOf(result.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    public static String GetRecord() {
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/getRecord";
        String result = "";
        try {
            HttpURLConnection conn = init(address, "");
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                result = parseInfo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String parseInfo(InputStream inputStream){
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
