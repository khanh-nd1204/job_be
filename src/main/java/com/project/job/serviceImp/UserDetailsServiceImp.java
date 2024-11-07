package com.project.job.serviceImp;

import com.project.job.entity.UserEntity;
import com.project.job.service.UserService;
import com.project.job.util.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UnauthorizedException {
        UserEntity user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UnauthorizedException("Invalid email or password");
        }
        if (!user.isActive()) {
            throw new UnauthorizedException("User account is inactive");
        }
        String role = user.getRoleId() == 1 ? "ROLE_ADMIN" : "ROLE_USER";
        return new User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
