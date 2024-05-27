package com.example.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.JwtResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.user.HotelUserDetails;
import com.example.demo.service.IUserServices;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final IUserServices userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    
    public AuthController(IUserServices userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

	@PostMapping("/register-user")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return ResponseEntity.ok("Registration successful!");

		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	 @PostMapping("/login")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
	        Authentication authentication =
	                authenticationManager
	                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
	        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
	        List<String> roles = userDetails.getAuthorities()
	                .stream()
	                .map(GrantedAuthority::getAuthority).toList();
	        return ResponseEntity.ok(new JwtResponse(
	                userDetails.getId(),
	                userDetails.getEmail(),
	                jwt,
	                roles));
	    }
}