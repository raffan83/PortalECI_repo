package it.portalECI.rest.secure;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;

public class JWTUtility {
	
	private static RsaJsonWebKey rsaJsonWebKey = null;
	
	static {
		//TODO: Using FILE to save and retrieve pub/priv key
		try {
		rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		rsaJsonWebKey.setKeyId("ECI-K");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getJWTAcessToken(UtenteDTO utente) {
		String result="";
		try {
		if(rsaJsonWebKey!=null) {
		
		
		JwtClaims claims = new JwtClaims();
	    claims.setIssuer("ECI-SPA");  // who creates the token and signs it
	    claims.setAudience("ECI-OPERATOR-APP"); // to whom the token is intended to be sent
	    claims.setExpirationTimeMinutesInTheFuture(60 * 24); // time when the token will expire (24 hours from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(1); // time before which the token is not yet valid (1 minutes ago)
	    claims.setSubject(utente.getUser()); // the subject/principal is whom the token is about
	    claims.setStringClaim("identity",""+utente.getId()); // additional claims/attributes about the subject can be added
	    
	    JsonWebSignature jws = new JsonWebSignature();
	    jws.setPayload(claims.toJson());
	    jws.setKey(rsaJsonWebKey.getPrivateKey());
	    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
	    result = jws.getCompactSerialization();
		}
		}catch(Exception ex) {
		ex.printStackTrace();	
			
		}
		
		return result;
	}
	
	public static UtenteDTO validateToken(String token) {
		UtenteDTO result=null;
		try {
			 JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			            .setRequireExpirationTime() // the JWT must have an expiration time
			            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
			            .setRequireSubject() // the JWT must have a subject claim
			            .setExpectedIssuer("ECI-SPA") // whom the JWT needs to have been issued by
			            .setExpectedAudience("ECI-OPERATOR-APP") // to whom the JWT is intended for
			            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
			            .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
			                    new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
			                            AlgorithmIdentifiers.RSA_USING_SHA256))
			            .build(); // create the JwtConsumer instance
			 JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			 String username = jwtClaims.getSubject();
			 String id = (String)jwtClaims.getClaimsMap().get("identity");
			 UtenteDTO utente= GestioneAccessoDAO.retrieveByJWSINfo(username, Integer.parseInt(id));
			if(utente!=null)
				result=utente;
		}catch(Exception ex) {
			ex.printStackTrace();	
			
		}
		
		return result;
	}

}
