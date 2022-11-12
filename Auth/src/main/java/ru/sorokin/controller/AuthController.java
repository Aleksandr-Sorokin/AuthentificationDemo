package ru.sorokin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sorokin.model.TokenResponse;
import ru.sorokin.model.User;
import ru.sorokin.service.client.ClientService;
import ru.sorokin.service.token.TokenService;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClientService clientService;
    private final TokenService tokenService;
    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        clientService.register(user.getClientId(), user.getClientSecret());
        return ResponseEntity.ok("Зарегистрирован");
    }

    @GetMapping(path = "/token")
    public TokenResponse getToken(@RequestBody User user) {
        clientService.checkCredentials(user.getClientId(), user.getClientSecret());
        return new TokenResponse(tokenService.generationToken(user.getClientId()));
    }

    @GetMapping(path = "/token/reissue")
    public TokenResponse reissueToken(@RequestParam String clientId) {
        clientService.checkClientId(clientId);
        return new TokenResponse(tokenService.generationToken(clientId));
    }
}
