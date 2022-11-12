package ru.sorokin.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public String generationToken(String clientId) {
        StringBuilder token = new StringBuilder("Token " + createToken(clientId, 3) +
                " TOKEN " + createToken(clientId, 5));
        return String.valueOf(token);
    }

    private String createToken(String clientId, int minutes) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
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
