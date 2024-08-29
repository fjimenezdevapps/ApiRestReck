package com.fjdev.rackapirest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fjdev.rackapirest.model.User;
import com.fjdev.rackapirest.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        User user =  userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("The user with email " + email + " dosen't exist. "));

        return new UserDetailsImpl(user);
    }


}
