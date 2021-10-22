package com.br.italo.usuarioService.config;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.br.italo.usuarioService.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	private String secretKet;
	private Long validateTime;
	private final String ROLES_KEY = "roles";
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
	
	@Autowired
	public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKet, 
			@Value("${security.jwt.token.expiration}") String validateTime) {
		super();
		this.secretKet = Base64.getEncoder().encodeToString(secretKet.getBytes());
		this.validateTime = Long.parseLong(validateTime);
	}
	
	public String createToken(String username , List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put(ROLES_KEY, roles.stream().map(r -> new SimpleGrantedAuthority(r.getAuthority()))
				 							.filter(Objects::nonNull)
				 							.collect(Collectors.toList()));	
		Date now = new Date();
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(new Date(now.getTime() + validateTime))
					.signWith(SignatureAlgorithm.HS256, secretKet)
					.compact();
	}
	
	public boolean isValidToken(String token) {
		 try {
	            Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token);
	            return true;
	        } catch (JwtException | IllegalArgumentException e) {
	        	//LOGGER.error("token is not valid",e);
	        	return false;
	        }
	}
	
	
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody().getSubject();
	}

	public List<GrantedAuthority> getRoles(String token){
		List<Map<String,String>> clams = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody().get(ROLES_KEY,List.class);
		clams.stream().forEach(System.out::println);
		return clams.stream().map(role ->  new SimpleGrantedAuthority(role.get("authority"))).collect(Collectors.toList());
	}
}
