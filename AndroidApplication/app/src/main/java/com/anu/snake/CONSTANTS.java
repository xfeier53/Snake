package com.anu.snake;

import java.util.regex.Pattern;

public interface CONSTANTS {
    public static final String PORT = "8080";
    public static final String IP = "172.20.10.5";
    public static final int LOGIN_VALIDATION = 1;
    public static final int REGISTER_VALIDATION = 2;
    public static final int MAIN_REQUEST_REGISTER = 3;
    public static final int REGISTER_RESPONSE_MAIN = 4;
    public static final int SCORE_PROCESS = 5;
    public static final int RECORD_PROCESS = 6;
    public final static Pattern EMAIL_PATTERN = Pattern.compile("u[0-9]{7}\\@anu.edu.au");
}
