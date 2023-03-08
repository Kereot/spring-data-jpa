package com.gb.market.auth.controllers;

import com.gb.market.api.dto.AuthRequestDto;
import com.gb.market.api.dto.AuthResponseDto;
import com.gb.market.api.dto.RegistrationUserDto;
import com.gb.market.api.exceptions.AppError;
import com.gb.market.auth.services.JwtService;
import com.gb.market.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
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

    @PostMapping("/registration")
    public ResponseEntity<?> createAuthToken(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords don't match!"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Username is taken, please try another one!"), HttpStatus.BAD_REQUEST);
        }
        userService.createUser(registrationUserDto);
        UserDetails user = userService.loadUserByUsername(registrationUserDto.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
