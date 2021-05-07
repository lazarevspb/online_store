package ru.lazarev.online_store.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

@Api("Set of endpoints for new user authentication")
public class AuthController {
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    @ApiOperation("Creates token for user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", defaultValue = "bob1", required = true,
                    dataTypeClass = String.class, type = "String", paramType = "query",
                    value = "Username"),
            @ApiImplicitParam(name = "password", defaultValue = "111", required = true,
                    dataTypeClass = String.class, type = "String", paramType = "query",
                    value = "Password")})
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                jwtRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
