package com.fzerey.user.host.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.UserAttribute;
import com.fzerey.user.infrastructure.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
                final var user = userRepository.findBySubId(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + "not found"));
                var attributes = user.getUserAttributes();
                var roles = attributes.stream().filter(attr -> attr.getAttribute().getKey().equals("roles")).findFirst().map(UserAttribute::getValue).orElse("none");
                return org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password(user.getHashedPassword())
                        .authorities(roles)
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build();
            }

}
