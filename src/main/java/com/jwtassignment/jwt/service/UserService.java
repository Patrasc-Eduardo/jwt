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
  private final Map<String, List<String>> userDetails = new HashMap<>();

  public UserService() {
    userDetails.put("Ana", Arrays.asList("ana@gmail.com", "anapasswd"));
    userDetails.put("Edi", Arrays.asList("edi@gmail.com", "edipasswd"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    return new User(username, userDetails.get(username).get(0), authorities);
  }

  public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
    Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : userDetails.entrySet()) {
      if (entry.getValue().get(0).compareTo(email) == 0) {
        return new User(entry.getKey(), entry.getValue().get(1), authorities);
      }
    }
    return null;
  }
}
