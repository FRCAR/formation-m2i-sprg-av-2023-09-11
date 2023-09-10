package com.bigcorp.web.jwt;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * Génère des jetons JWT
 */
public class JwtGenerator {

	public static void main(String[] args) throws KeyLengthException, JOSEException, ParseException {
		SecureRandom random = new SecureRandom();
		byte[] sharedSecrets = new byte[32];
		random.nextBytes(sharedSecrets);

		String secretString = Base64.getEncoder().encodeToString(sharedSecrets);

		secretString = "os9eB1DXbGrS637mK5sESWN7mAwmNE57McstI2YV/TE=";

		System.out.println(secretString);

		// Create HMAC signer
		JWSSigner signer = new MACSigner(secretString);

		// Prepare JWT with claims set
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("alice")
				.issuer("https://c2id.com")
				.expirationTime(new Date(new Date().getTime() + 60_000 * 1000))
				.notBeforeTime(new Date())
				.claim("authorities", "coucou")
				.build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		// Apply the HMAC protection
		signedJWT.sign(signer);

		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		String s = signedJWT.serialize();
		System.out.println("Le jeton vaut : ");
		System.out.println(s);

		// On the consumer side, parse the JWS and verify its HMAC
		signedJWT = SignedJWT.parse(s);

		JWSVerifier verifier = new MACVerifier(secretString);

		System.out.println("Jeton vérifié : " + signedJWT.verify(verifier));

		// Retrieve / verify the JWT claims according to the app requirements
		System.out.println("subject : " + signedJWT.getJWTClaimsSet().getSubject());
		System.out.println("issuer : " + signedJWT.getJWTClaimsSet().getIssuer());
		System.out.println("Expiration time : " + signedJWT.getJWTClaimsSet().getExpirationTime());
	}

}
