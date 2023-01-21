package cz.osu.vbap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.osu.vbap.model.auth.LoginCredentials;
import cz.osu.vbap.model.auth.RegisterCredentials;
import cz.osu.vbap.model.dto.TokenDTO;
import cz.osu.vbap.service.AuthService;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentials login) {
        try {
            TokenDTO token = this.authService.login(login);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterCredentials register) {
        try {
            authService.register(register);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
