package com.jobbies.jobbies.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class UtilSecurityJWT {
	
	@Value("${security.jwt.key-secret}")
	private String keySecret;
	
	public String createToken(Authentication authentication) {
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256(keySecret);
		    
		    String username = authentication.getPrincipal().toString(); //principal contiene le user que se autentico.
		    String authorities = authentication.getAuthorities().stream()
		    		.filter(g -> g != null)
		    		.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));  //obtenemos los permisos: READM, CREATE, etc
		    return JWT.create()
		        .withIssuer("Jobbies-back")
		        .withSubject(username)
		        .withClaim("Authorities", authorities)
		        .withIssuedAt(this.generateDateNow())
		        .withExpiresAt(this.generateDateExpired())
		        .withJWTId(UUID.randomUUID().toString())
		        .withNotBefore(generateDateNow())
		        .sign(algorithm);
		    
		} catch (JWTCreationException exception){
		    System.out.println("Error al crear token:".concat(exception.getMessage()));
		}
		return null;
	}
	
	public DecodedJWT validateToken(String token) {
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256(keySecret);
		    JWTVerifier verifier = JWT.require(algorithm)
		        // specify any specific claim validations
		        .withIssuer("Jobbies-back")
		        // reusable verifier instance
		        .build();
		        
		    DecodedJWT decodedJWT = verifier.verify(token);
		    // Verificación explícita de la expiración (opcional, pero recomendado)
	        if (decodedJWT.getExpiresAt().before(new Date())) {
	            System.out.println("Token expirado");
	            return null;
	        }
		    return decodedJWT;
		} catch (JWTVerificationException e){
		    System.out.println("Token invalid: ".concat(e.getMessage()));
		}
		return null;
		
	}
	
	


	public Claim getSpecificClaim(DecodedJWT tokenDecoded, String nameClaim) {
		return tokenDecoded.getClaim(nameClaim);
	}

	public String extractUsername(DecodedJWT tokenDecoded) {
		return tokenDecoded.getSubject().toString();
	}
	
	private Instant generateDateExpired() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
	
	private Instant generateDateNow() {
		return LocalDateTime.now().toInstant(ZoneOffset.of("-05:00"));
	}

}
