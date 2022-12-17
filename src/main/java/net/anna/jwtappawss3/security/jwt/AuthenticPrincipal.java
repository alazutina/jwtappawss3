package net.anna.jwtappawss3.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticPrincipal {
    public static final AuthenticPrincipal INSTANCE = new AuthenticPrincipal();
    public JwtUser getPrincipal() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
