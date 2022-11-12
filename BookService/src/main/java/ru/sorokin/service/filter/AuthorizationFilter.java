package ru.sorokin.service.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sorokin.service.token.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    @Value("${auth.enabled}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!enabled) {
            filterChain.doFilter(request, response);
        }
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        String authShort = null;
        String authLong = null;
        if (auth != null) {
            int index = auth.lastIndexOf("TOKEN ");
            authShort = auth.substring(6, index - 2);
            authLong = auth.substring(index + 6);
        }
        if ((authShort == null || authShort.isBlank())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (!checkAuthorization(authShort, authLong)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkAuthorization(String authShort, String authLong) {
        System.out.println("checkAuthorization");
        if (!authShort.startsWith("Token ") && !authLong.startsWith("Token ")) {
            return false;
        }
        String tokenShort = authShort.substring(6);
        String tokenLong = authLong.substring(6);
        if (tokenService.checkToken(tokenShort)) {
            return true;
        } else if (tokenService.checkToken(tokenLong)) {
            tokenService.createNewToken(tokenLong);
            return true;
        } else {
            return false;
        }
    }
}
