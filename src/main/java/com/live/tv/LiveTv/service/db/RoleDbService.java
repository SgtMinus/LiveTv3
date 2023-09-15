package com.live.tv.LiveTv.service.db;

import com.live.tv.LiveTv.entity.Role;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoleDbService {
    private final String ENTITY_ROLE_CLASS_NAME = "Роль";
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));

    }
}

