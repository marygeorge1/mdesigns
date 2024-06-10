package com.sparta.mdesigns.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userName="dummyUser";
        String password="dummyPassword";
        List<GrantedAuthority> authorities=null;
        authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("customer"));

        return new org.springframework.security.core.userdetails.User(userName,password,authorities);

    }
}
