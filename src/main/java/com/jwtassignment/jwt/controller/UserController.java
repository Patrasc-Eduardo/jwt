package com.jwtassignment.jwt.controller;

import com.jwtassignment.jwt.model.User;
import com.jwtassignment.jwt.payload.request.AuthenticationRequest;
import com.jwtassignment.jwt.payload.response.AuthenticationResponse;
import com.jwtassignment.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final JWTUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      System.err.println(authenticationRequest.toString());
      System.err.println(authenticationRequest.getUsername());
      System.err.println(authenticationRequest.getPassword());
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  authenticationRequest.getUsername(), authenticationRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (BadCredentialsException e) {

      throw new Exception("Incorrect username or password");
    }
    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    System.err.println(jwt);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  @PostMapping(value = "/register")
  public String registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
    User user = new User();
    user.setUsername(authenticationRequest.getUsername());
    user.setPassword(bCryptPasswordEncoder.encode(authenticationRequest.getPassword()));
    return "The user was created.";
  }
}
