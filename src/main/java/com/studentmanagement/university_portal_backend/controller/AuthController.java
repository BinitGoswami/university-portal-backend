package com.studentmanagement.university_portal_backend.controller;

import com.studentmanagement.university_portal_backend.config.JwtUtils;
import com.studentmanagement.university_portal_backend.entity.User;
import com.studentmanagement.university_portal_backend.repository.UserRepository;
import com.studentmanagement.university_portal_backend.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // 1. Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // 2. Hash the password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Force the role to uppercase just to be safe (e.g., "admin" -> "ADMIN")
        user.setRole(user.getRole().toUpperCase());

        // 4. Save the user
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        try {
            // 1. Spring Security checks the password against the database
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }

        // 2. If the password is correct, fetch the user details (roles)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // 3. Generate the token and sent it back to the client!
        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}
