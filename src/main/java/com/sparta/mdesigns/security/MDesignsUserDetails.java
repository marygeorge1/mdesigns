
package com.sparta.mdesigns.security;


import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MDesignsUserDetails implements UserDetailsService {



    private UsersRepository userRepo;

    @Autowired
    public MDesignsUserDetails(UsersRepository userRepo) {

        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       String userName,password;
        List<GrantedAuthority> authorities=null;

        User user=userRepo.findByEmail(username);

        if(user==null){
            System.out.println("***********user does not exist************");
            throw new UsernameNotFoundException("User details not found for user: "+username);
        }
        else {
            userName=user.getEmail();
            password=user.getPassword();
            authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("customer"));

            System.out.println(user.getName()+"***********"+userName+"************"+password);
        }

        return new org.springframework.security.core.userdetails.User(userName,password,authorities);
    }
}


