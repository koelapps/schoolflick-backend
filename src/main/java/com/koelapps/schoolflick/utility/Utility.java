package com.koelapps.schoolflick.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Utility {
	
	public static String encrypt(String value) {
	
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = "Asad";
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println();
		System.out.println("Password is         : " + password);
		System.out.println("Encoded Password is : " + encodedPassword);
		System.out.println();
		return encodedPassword;
	}
	
	public static boolean validatePassword(String password, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
		return isPasswordMatch;
	}
	
}
