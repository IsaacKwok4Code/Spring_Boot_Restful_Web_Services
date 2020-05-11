package com.yin4learning.restful_web_service.share.util;

import java.util.Random;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private String generateRandomString(int length) {
		 StringBuilder returnValue = new StringBuilder(length);
		 for (int i = 0; i < length; i++) {
			 returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		 }
		 return new String(returnValue);
	}
	
	public String generateUserId(int length) {
		 return generateRandomString(length);
	 }
	
	public String generateAddressId(int length) {
	     return generateRandomString(length);
	 }
}
