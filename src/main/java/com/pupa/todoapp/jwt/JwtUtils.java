package com.pupa.todoapp.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pupa.todoapp.services.UserDetailsImplementation;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${todoapp.jwtSecret}")
	private String jwtSecret;
	
	@Value("${todoapp.jwtExpiration}")
	private int jwtExpiration;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImplementation userPrincipal = (UserDetailsImplementation) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date().getTime()) + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("invalid jwt signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("invalid jwt token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("jwt token is expired: {}", e.getMessage());
		} catch(UnsupportedJwtException e) {
			logger.error("jwt token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("jwt claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}
