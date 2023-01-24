package com.gb.api;

import com.gb.dto.AuthRequestDto;
import com.gb.dto.AuthResponseDto;
import com.gb.exceptions.AppError;
import com.gb.services.JwtService;
import com.gb.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto request) {
        log.info("Request from {}", request.getUsername());
        log.info(userService.loadUserByUsername(request.getUsername()).getPassword());
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Incorrect username or password"),HttpStatus.UNAUTHORIZED);
        }
//        UserDetails user = (UserDetails) authentication.getPrincipal();
        UserDetails user = userService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
