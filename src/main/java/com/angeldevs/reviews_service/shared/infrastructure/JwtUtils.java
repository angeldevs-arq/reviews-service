package com.angeldevs.reviews_service.shared.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
    public record JwtPayload(String sub, Long iat, Long exp) {
    }

    public static JwtPayload decode(String token) {
        DecodedJWT decoded = JWT.decode(token.replace("Bearer ", ""));
        return new JwtPayload(
                decoded.getSubject(),
                decoded.getClaim("iat").asLong(),
                decoded.getClaim("exp").asLong());
    }
}
