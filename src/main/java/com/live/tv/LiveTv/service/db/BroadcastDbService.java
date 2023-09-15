package com.live.tv.LiveTv.service.db;

import com.live.tv.LiveTv.entity.Broadcast;
import com.live.tv.LiveTv.repository.BroadcastRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BroadcastDbService {
    private final BroadcastRepository broadcastRepository;

    @Transactional(readOnly = true)
    public Page<Broadcast> findAll(Pageable pageable) {
        return broadcastRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Broadcast> findAllByName(String name, Pageable pageable) {
        return broadcastRepository.findAllByName(name, pageable);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long broadcastId) {
        return broadcastRepository.existsById(broadcastId);
    }
}

