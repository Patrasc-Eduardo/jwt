package com.jwtassignment.jwt.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
  // userRepo simulation
  private final Map<String, String> userDetails = new HashMap<>();

  public UserService() {
    userDetails.put("Ana", "anapasswd");
    userDetails.put("Edi", "edipasswd");
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    return new User(username, userDetails.get(username), authorities);
  }

}
