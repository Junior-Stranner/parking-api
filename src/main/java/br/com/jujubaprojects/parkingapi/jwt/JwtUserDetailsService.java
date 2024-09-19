package br.com.jujubaprojects.parkingapi.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserDetailsService extends UserDetailsService {

    JwtToken getTokenAuthenticated(String username);
}