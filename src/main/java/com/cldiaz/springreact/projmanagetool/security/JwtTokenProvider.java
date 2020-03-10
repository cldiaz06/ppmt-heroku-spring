package com.cldiaz.springreact.projmanagetool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.cldiaz.springreact.projmanagetool.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import javassist.bytecode.stackmap.BasicBlock.Catch;

import static com.cldiaz.springreact.projmanagetool.security.SecurityConstants.EXPIRATION_TIME;
import static com.cldiaz.springreact.projmanagetool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

	//generate token
	public String generateToken(Authentication auth) {
		User user = (User)auth.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expireDate = new Date(now.getTime() + EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullName());
		
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
			
	}
	
	//validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			// TODO: handle exception
			System.out.println("Invalid JWT Signature");
		} catch(MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		} catch(ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");
		} catch(UnsupportedJwtException ex) {
			System.out.println("Unsupport JWT Token");
		} catch(IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		
		return false;
	}

	//get user from token
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		return Long.parseLong(id);
	}
	
}
