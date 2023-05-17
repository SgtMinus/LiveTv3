package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.entity.Broadcast;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.service.db.BroadcastDbService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BroadcastService {
    private final String BROADCAST_ENTITY_NAME = "Трансляция";

    private final BroadcastDbService broadcastDbService;

    public List<Broadcast> findAll(int page, int pageSize) {
        return broadcastDbService.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public List<Broadcast> findAllByName(String name, int page, int pageSize) {
        if (name.isBlank()) {
            throw new RuntimeException("Имя не может быть пустым/null!");
        }
        return broadcastDbService.findAllByName(name, PageRequest.of(page, pageSize)).getContent();
    }

    public boolean existsById(Long broadcastId) {
        return broadcastDbService.existsById(broadcastId);
    }

    public void checkBroadcastExists(Long broadcastId) {
        if (!existsById(broadcastId)) {
            throw new EntityNotFoundException(BROADCAST_ENTITY_NAME);
        }
    }
}
