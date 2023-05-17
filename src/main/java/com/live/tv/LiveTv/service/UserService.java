package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.converter.UserConverter;
import com.live.tv.LiveTv.dto.UserDto;
import com.live.tv.LiveTv.entity.Role;
import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.exception.BannedUserException;
import com.live.tv.LiveTv.exception.EntityAlreadyExistsException;
import com.live.tv.LiveTv.exception.ReputationException;
import com.live.tv.LiveTv.exception.WrongAuthException;
import com.live.tv.LiveTv.security.jwt.JwtTokenProvider;
import com.live.tv.LiveTv.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final String ENTITY_CLASS_NAME = "Пользователь";
    private final UserDbService userDbService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserConverter userConverter;
    private final RolesService rolesService;

    public String auth(UserDto authUser) throws AuthException {
        User userFromDataBase = userDbService.findUserByEmail(authUser.getEmail());

        if (passwordEncoder.matches(authUser.getPassword(), userFromDataBase.getPassword())) {
            return jwtTokenProvider.createToken(authUser.getEmail(), userFromDataBase.getRoles());
        }

        throw new WrongAuthException();
    }

    public String register(UserDto authUser) {
        if (userDbService.existsByEmail(authUser.getEmail())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        List<Role> userRoles = rolesService.getDefaultRoles();

        User user = userConverter.toEntity(authUser, userRoles);

        userDbService.create(user);

        return jwtTokenProvider.createToken(authUser.getEmail(), userRoles);
    }


    public Long getUserIdFromContext() {
        return getUserFromContext().getId();
    }

    public User getUserById(Long userId) {
        return userDbService.findUserById(userId);
    }

    public User getUserFromContext() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDbService.findUserByEmail(userName);
    }

    public void changeInterest(Long userId, Long interestValue, Long interestValueBefore) {
        if (interestValue != null && interestValue.equals(interestValueBefore)) return;
        User user = userDbService.findUserById(userId);
        user.setReputation(user.getReputation() == null ? 0 : user.getReputation() + interestValue - interestValueBefore);
        userDbService.create(user);
    }

    public void changeInterest(Long userId, Long interestValue) {
        User user = userDbService.findUserById(userId);
        user.setReputation(user.getReputation() == null ? 0 : user.getReputation() + interestValue);
        userDbService.create(user);
    }

    public void checkBannedUser(User user) {
        if (isBannedUser(user)) {
            throw new BannedUserException(user.getEmail());
        }
    }

    public boolean isBannedUser(User user) {
        return user.getBanned() != null && user.getBanned().after(new Date());
    }

    public void checkReputationJudgeGreaterAuthor(User judge, User author) {
        if (judge.getReputation() < author.getReputation()) {
            throw new ReputationException();
        }
    }

    public void setBanToUser(User user) {
        Date bannedBefore = Date.from(Instant.now().plus(Duration.ofDays(1)));
        user.setBanned(bannedBefore);
        userDbService.create(user);
    }

}
