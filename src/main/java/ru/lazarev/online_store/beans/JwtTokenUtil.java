package ru.lazarev.online_store.beans;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails details) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = details.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);

        Date issueDate = new Date();
        Date expires = new Date(issueDate.getTime() + 15 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(details.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public List<String> getRolesFromToken(String token) {
        return getClaimFromToken(token,
                (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

}
