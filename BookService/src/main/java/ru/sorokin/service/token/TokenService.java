package ru.sorokin.service.token;

import java.util.Map;

public interface TokenService {
    boolean checkToken(String token);
    String createNewToken(String clientId);
}
