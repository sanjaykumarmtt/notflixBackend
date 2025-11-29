package com.example.NetfixClone.Util;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	

	private String key="shvsdyv7dy83gz1xtex73gug18ehxb81y911e2h";
	
	private SecretKey secretKey=Keys.hmacShaKeyFor(key.getBytes());
	
	public String createToken(UserDetails userDetails) {
		
	 return	Jwts.builder()
		    .subject(userDetails.getUsername())
		    .issuedAt(new Date())
		    .expiration(new Date(System.currentTimeMillis()+1000*60*60))
		    .signWith(secretKey)
		    .compact();
		
	}
	
	public String extractUserName(String token) {
		
	 return	Jwts.parser()
		    .verifyWith(secretKey)
		    .build()
		    .parseSignedClaims(token)
		    .getPayload()
		    .getSubject();
		
	}
	
	public boolean vaildateToken(UserDetails userDetails,String token) {
		
		return extractUserName(token).equals(userDetails.getUsername());
		
	}

}
