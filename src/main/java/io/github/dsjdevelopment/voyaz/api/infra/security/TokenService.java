package io.github.dsjdevelopment.voyaz.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.dsjdevelopment.voyaz.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("API Voyaz").withSubject(user.getLogin()).withClaim("id", user.getId())
                    .withExpiresAt(expirationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("error to generate token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("API Voyaz")
                    // reusable verifier instance
                    .build().verify(tokenJWT).getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired Jwt token!", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
