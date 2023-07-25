package fr.bbillebault.apitest.backend.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil {
	private static final long TOKEN_VALIDITY_PERIOD = 1000 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		
		long time = System.currentTimeMillis();
		
		return Jwts
			.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(time))
			.setExpiration(new Date(time + TokenUtil.TOKEN_VALIDITY_PERIOD))
			.signWith(SignatureAlgorithm.HS512, this.secret)
			.compact();
	}
	
	public String getTokenUsername(String token) {
		return this.getTokenClaim(token, Claims::getSubject);
	}
	
	public Date getTokenExpirationDate(String token) {
		return this.getTokenClaim(token, Claims::getExpiration);
	}
	
	public <T> T getTokenClaim(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(this.getTokenClaims(token));
	}
	
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		return userDetails.getUsername().equals(this.getTokenUsername(token)) && !this.isTokenExpired(token);
	}
	
	public Boolean isTokenExpired(String token) {
		return this.getTokenExpirationDate(token).before(new Date());
	}
	
	private Claims getTokenClaims(String token) {
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	}
}
