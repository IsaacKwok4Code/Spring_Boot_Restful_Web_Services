package com.yin4learning.restful_web_service.security;

public class SecurityConstants {
	//token time
	public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    //token header
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    //token_encode
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
    //POST call for creating user end-point
    public static final String SIGN_UP_URL = "/RestfulController/createUser";
    /*
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/users/password-reset";
    public static final String H2_CONSOLE = "/h2-console/**";
    */
    
}
