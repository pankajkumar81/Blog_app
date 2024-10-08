package pankaj.blog.security;



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
public class JwtTokenHelper {
	
					public static final long JWT_TOKEN_VALIDITY= 5*60*60;
		
					@Value("${jwt.secret}")
					private String secret;
		
					//Retrieve username from token
					public String getUsernameFromToken(String token) {
						return getClaimFromToken(token,Claims::getSubject);
					}
		
					//Retrieve Expiration from token
					public Date getExpirationDateFromToken(String token) {
						return getClaimFromToken(token,Claims::getExpiration);
					}
		
					public <T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver) {
						final Claims claims= getAllClaimFromToken(token);
						return claimsResolver.apply(claims);
					}
					
					//Retrieve any information from token
					private Claims getAllClaimFromToken(String token)
					{
						return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
					}
					
					private Boolean isTokenExpired(String token)
					{
						final Date expiration= getExpirationDateFromToken(token);
						return expiration.before(expiration);
					}
					
					public String generateToken(UserDetails userDetails)
					{
						Map<String, Object> claims= new HashMap<>();
						return doGenerateToken(claims,userDetails.getUsername());
					}
					private String doGenerateToken(Map<String,Object> claims, String subject)
					{
						return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
								.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*100))
										.signWith(SignatureAlgorithm.HS512, secret).compact();
					}
					
					public Boolean validateToken(String token,UserDetails userDetails)
					{
						final String username=getUsernameFromToken(token);
						return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
					}
	}