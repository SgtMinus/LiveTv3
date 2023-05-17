package com.live.tv.LiveTv.service.db;

import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDbService {
    private final String ENTITY_CLASS_NAME = "Пользователь";
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public List<User> findAllByReputationGreaterThan(Long reputation) {
        return userRepository.findAllByReputationGreaterThan(reputation);
    }

}
