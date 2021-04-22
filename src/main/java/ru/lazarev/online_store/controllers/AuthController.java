package ru.lazarev.online_store.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lazarev.online_store.beans.JwtTokenUtil;
import ru.lazarev.online_store.dto.JwtRequest;
import ru.lazarev.online_store.dto.JwtResponse;
import ru.lazarev.online_store.services.UserService;


@RestController
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                jwtRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
