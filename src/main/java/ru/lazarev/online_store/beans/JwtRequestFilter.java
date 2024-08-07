package ru.lazarev.online_store.beans;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);

            userName = jwtTokenUtil.getUsernameFromToken(jwt);
            Object userDetails;
            Object principal;
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userName,
                            null, jwtTokenUtil
                            .getRolesFromToken(jwt)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
