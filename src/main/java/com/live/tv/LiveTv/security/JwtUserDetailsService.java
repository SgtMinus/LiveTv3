package com.live.tv.LiveTv.security;

import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.security.jwt.JwtUserFactory;
import com.live.tv.LiveTv.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDbService userDbService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDbService.findUserByEmail(email);

        return JwtUserFactory.create(user);
    }
}
