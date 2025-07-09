package com.antonio.apprendrebackend.service.config.jwt;

import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserInfoService userInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = jwtService.getSupabaseIdFromToken(token);

        UserInfo userInfo = userInfoService.findBySupabaseId(username);

        if (jwtService.isTokenValid(token, userInfo)) {
            return new UsernamePasswordAuthenticationToken(
                    userInfo,
                    null,
                    null);
        }

        throw new AuthenticationException("Invalid JWT token") {
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
