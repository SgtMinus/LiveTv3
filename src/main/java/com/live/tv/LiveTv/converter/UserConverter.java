package com.live.tv.LiveTv.converter;

import com.live.tv.LiveTv.dto.UserDto;
import com.live.tv.LiveTv.entity.Role;
import com.live.tv.LiveTv.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserConverter {
    private final BCryptPasswordEncoder passwordEncoder;

    public User toEntity(UserDto authUser, List<Role> userRoles) {
        User user = new User();

        user.setEmail(authUser.getEmail());
        user.setPassword(passwordEncoder.encode(authUser.getPassword()));
        user.setRoles(userRoles);

        return user;
    }
}
