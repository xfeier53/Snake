package com.anu.snake;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPConnection {

    static final String PORT = "8808";

    public static String LoginByPost(String id, String password) {
        String address = "http://localhost:" + PORT + "/login";
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
            String data = "id=" + URLEncoder.encode(id, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
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

    public static String RegisterByPost(String id, String password, String email) {
        String address = "http://localhost:" + PORT + "/register";
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
            String data = "id=" + URLEncoder.encode(id, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8");
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

}
