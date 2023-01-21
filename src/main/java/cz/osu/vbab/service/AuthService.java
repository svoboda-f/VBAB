package cz.osu.vbab.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.osu.vbab.model.AppUserDetails;
import cz.osu.vbab.model.User;
import cz.osu.vbab.model.auth.LoginCredentials;
import cz.osu.vbab.model.auth.RegisterCredentials;
import cz.osu.vbab.model.dto.TokenDTO;
import cz.osu.vbab.repository.UserRepository;
import cz.osu.vbab.utils.JwtTokenUtil;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenDTO login(LoginCredentials login) throws Exception {
        Authentication authentication;
        try {
            authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));
        } catch (DisabledException | BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
        UserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new TokenDTO(token);
    }

    public User register(RegisterCredentials register) throws Exception {
        Optional<User> optionalUser = this.userRepository.findByUsername(register.username());

        if (optionalUser.isPresent()) {
            throw new Exception("User with name " + register.username() + " already exists");
        }

        optionalUser = this.userRepository.findByEmail(register.email());

        if (optionalUser.isPresent()) {
            throw new Exception("Email " + register.email() + " already in use");
        }
        User user = new User(register.email(), register.username(), passwordEncoder.encode(register.password()),
                register.firstName(), register.lastName(), register.dateOfBirth());
        return this.userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findByUsername(authentication.getName()).get();
    }

    public long getCurrentUserId() {
        return this.getCurrentUser().getId();
    }
}
