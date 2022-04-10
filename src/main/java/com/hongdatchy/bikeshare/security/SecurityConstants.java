package com.hongdatchy.bikeshare.security;

public class SecurityConstants {
    public static final String SECRET = "randomString";
    public static final long EXPIRATION_TIME = 86400000 * 7; // 7 day
//    public static final long EXPIRATION_TIME = 86400000/24/60/2; // 30s
    public static final String TOKEN_PREFIX = "hongdatchy";
}
