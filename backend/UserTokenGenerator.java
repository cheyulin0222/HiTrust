package com.hitrsutpay.sa.backend;

import org.springframework.security.core.userdetails.User;

import com.hitrsutpay.sa.backend.security.JwtUtils;

public class UserTokenGenerator {
	
	public static String generateAccessToken() {
		// return generateAccessToken("yushianghuang@hitrust.com.tw" , "8brr59em" , "admin" );
		return generateAccessToken("jasonhsiao@hitrust.com" , "jasonhitrust1022" , "admin" );
		//return generateAccessToken("jasonhitrust1022@gmail.com" , "6ts7gzf8kvbt" , "admin" );
	}
	
	public static String generateAccessToken(String userId , String password , String role) {
		return JwtUtils.generateToken(
				User.builder()
				.username	(	userId		)
				.password	(	password	)
				.roles		(	role 		)
				.build()
		);
	}
	
	public static void main(String[] args) {
		System.out.println(generateAccessToken());
	}

}
