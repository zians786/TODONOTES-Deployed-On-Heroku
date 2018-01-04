package com.bridgeit.utility;

import org.springframework.stereotype.Component;

import com.bridgeit.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWT {

	final String key = "qazxswedc";

	public String jwtGenerator(String id) {

		String jwToken = Jwts.builder()
				.setSubject("Registration")
				.setId(id)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();

		return jwToken;
	}

	public String jwtGenerator(int id) {
		String uId=Integer.toString(id);
		
		String jwToken = Jwts.builder()
				.setSubject("Registration")
				.setId(uId)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();

		return jwToken;
	}
	
	
	
	public String jwtVerify(String jwToken) {
		String status;
		try {
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwToken).getBody();
			status = claims.getId();
		} catch (Exception e) {
			status = null;
		}
		return status;
	}

	

	public int jwtVerifyToken(String jwToken) {
		int status;
		try {
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwToken).getBody();
			String id = claims.getId();
			status=Integer.parseInt(id);
			
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}

	
}
