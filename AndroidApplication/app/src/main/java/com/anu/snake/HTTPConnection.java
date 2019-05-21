package com.anu.snake;

import android.content.Intent;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPConnection {

    static final String PORT = "8080";
    static final String IP = "172.20.10.6";

    public static String LoginByPost(String account, String password) {
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/login";
        String result = "";
        try {
            // Connect with POST method
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // Time out
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // Set cache for Post
            conn.setUseCaches(false);
            // Request
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            // Get output stream
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                // Close resource
                is.close();
                message.close();
                // Return result
                result = new String(message.toByteArray());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String RegisterByPost(String account, String password, String email) {
        String address = "http://" + IP + ":" + PORT + "/AndroidServer/register";
        String result = "";
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // Time out
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // Set cache for Post
            conn.setUseCaches(false);
            // Request
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8");
            // Get output stream
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                // Close resource
                is.close();
                message.close();
                // Return result
                result = new String(message.toByteArray());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // Time out
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // Set cache for Post
            conn.setUseCaches(false);
            // Request
            String data = "account=" + URLEncoder.encode(account, "UTF-8");
            // Get output stream
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                // Get input stream and read by byte
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                // Close resource
                is.close();
                message.close();
                DataInputStream dataIs = new DataInputStream(new ByteArrayInputStream(message.toByteArray()));
                // Return result
                score = dataIs.readInt();
                Log.d("test2", account + score);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

}
