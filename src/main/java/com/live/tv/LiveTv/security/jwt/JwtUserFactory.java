package com.live.tv.LiveTv.security.jwt;

import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.service.RolesService;

public final class JwtUserFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getUpdated(),
                RolesService.mapToGrantedAuthorities(user.getRoles())
        );
    }
}
