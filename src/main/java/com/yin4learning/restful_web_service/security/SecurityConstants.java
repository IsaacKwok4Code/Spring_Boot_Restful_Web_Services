package com.yin4learning.restful_web_service.security;

import com.yin4learning.restful_web_service.SpringApplicationContext;

public class SecurityConstants {
	//token time
	public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    //token header
    public static final String TOKEN_PREFIX = "Isaac ";
    public static final String HEADER_STRING = "Authorization";
    //token_encode
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
    //POST call for creating user end-point
    public static final String SIGN_UP_URL = "/RestfulController/createUser";
    
    public static final String VERIFICATION_EMAIL_URL = "/RestfulController/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/RestfulController/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/RestfulController/password-reset";
    public static final String H2_CONSOLE = "/h2-console/**";
    
    
    public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
