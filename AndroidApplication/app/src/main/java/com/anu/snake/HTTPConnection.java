/*
Authorship: Feier Xiao
 */

package com.anu.snake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPConnection {
    // Init the connection, connect and retrive the result
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
            // Write in the request
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

    // Send request to login servlet, to retrieve the user profile for validation
    public static String loginByPost(String account, String password) {
        String result = "";
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/login";
        try {
            // Request content
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            HttpURLConnection conn = init(address, data);
            if (conn.getResponseCode() == 200) {
                // Get the intput stream
                InputStream is = conn.getInputStream();
                // Return result
                result = parseInfo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Send request to register servlet and add user into databse
    public static String registerByPost(String account, String password, String email) {
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/register";
        String result = "";
        try {
            // Request content
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8");
            HttpURLConnection conn = init(address, data);
            if (conn.getResponseCode() == 200) {
                // Get the input stream
                InputStream is = conn.getInputStream();
                // Return result
                result = parseInfo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Send request to GetBestScoreServlet servlet to retrieve user's best score
    public static int getBestScoreByPost(String account) {
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/getBestScore";
        String result = "";
        int score = 0;
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

    // Send request to RecordServlet to retrieve world record
    public static String getRecord() {
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/getRecord";
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

    // Send request to SetRecordServlet to set the new world record
    public static String setRecord(String recordString) {
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/setRecord";
        String result = "";
        try {
            HttpURLConnection conn = init(address, "recordString");
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

    // Send request to SetRecordServlet to set the new world record
    public static String setBestScoreByPost(String account, int thisScore) {
        String address = "http://" + CONSTANTS.IP + ":" + CONSTANTS.PORT + "/AndroidServer/setBestScore";
        String result = "";
        try {
            // Request content
            String data = "account=" + URLEncoder.encode(account, "UTF-8") + "&bestScore=" + thisScore;
            HttpURLConnection conn = init(address, data);
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

    // Tool to convert inputStream content into String
    public static String parseInfo(InputStream inputStream) {
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();
        try {
            // Init BufferedReader
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Read line
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // If the reader is not null, release the resource
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
