package com.module2.user;

import com.module2.shared.config.security.JwtUtils;
import com.module2.shared.error.ErrorMessage;
import com.module2.shared.model.user.AuthenticationRequest;
import com.module2.shared.model.user.User;
import com.module2.shared.model.user.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (!authenticate.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorMessage.INVALID_USERNAME_OR_PASSWORD.getMessage());
        }
        User user = userService.getUserByUsername(request.getUsername());
        return new ResponseEntity<>(new UserReadDto(user), getJwtHeader(user), HttpStatus.OK.value());
    }

    private HttpHeaders getJwtHeader(User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtUtils.JWT_TOKEN_HEADER, JwtUtils.generateToken(user));
        return httpHeaders;
    }
}
