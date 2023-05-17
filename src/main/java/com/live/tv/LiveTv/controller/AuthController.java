package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.dto.UserDto;
import com.live.tv.LiveTv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        String token = userService.register(userDto);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody UserDto userDto) throws AuthException {
        String token = userService.auth(userDto);
        return ResponseEntity.ok().body(token);
    }
}
