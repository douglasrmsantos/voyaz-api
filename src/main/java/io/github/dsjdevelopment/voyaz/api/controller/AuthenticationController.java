package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.user.AuthenticationData;
import io.github.dsjdevelopment.voyaz.api.domain.user.User;
import io.github.dsjdevelopment.voyaz.api.infra.security.JWTTokenData;
import io.github.dsjdevelopment.voyaz.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<JWTTokenData> logIn(@RequestBody @Valid AuthenticationData data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JWTTokenData(tokenJWT));
    }
}
