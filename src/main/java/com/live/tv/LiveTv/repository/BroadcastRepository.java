package com.live.tv.LiveTv.repository;

import com.live.tv.LiveTv.entity.Broadcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {
    Page<Broadcast> findAll(Pageable pageable);

    Page<Broadcast> findAllByName(String name, Pageable pageable);

    boolean existsById(Long id);
}
