package ru.sorokin.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sorokin.exceptions.TokenException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public boolean checkToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")) {
                log.error("Issuer is incorrect");
                return false;
            }
            if (!decodedJWT.getAudience().get(0).equals("Authentication-demo")) {
                log.error("Audience is incorrect");
                return false;
            }
        } catch (JWTVerificationException e) {
            log.error("Token is invalid: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String createNewToken(String lastToken) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        String tokens = null;
        try {
            DecodedJWT decodedJWT = verifier.verify(lastToken);
            if (decodedJWT.getIssuer().equals("auth-service")
                    && decodedJWT.getAudience().get(0).equals("Authentication-demo")) {
                String clientId = decodedJWT.getSubject();
                System.out.println(clientId);
                StringBuilder builder = new StringBuilder("Token " + generateToken(algorithm, clientId, 3) +
                        " TOKEN " + generateToken(algorithm, clientId, 5));
                tokens = String.valueOf(builder);
            }
        } catch (JWTVerificationException e) {
            log.error("Проблема с токеном " + e.getMessage());
            throw new TokenException("Не соответствует нужным параметрам " + e.getMessage());
        }
        return tokens;
    }

    private String generateToken(Algorithm algorithm, String clientId, int minutes) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer("auth-service")
                .withAudience("Authentication-demo")
                .withSubject(clientId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(minutes, ChronoUnit.MINUTES)))
                .sign(algorithm);
    }
}
