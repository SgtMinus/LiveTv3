package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.entity.Broadcast;
import com.live.tv.LiveTv.service.BroadcastService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/broadcasts")
@AllArgsConstructor
public class BroadcastController {
    private final BroadcastService broadcastService;

    @GetMapping
    public ResponseEntity<List<Broadcast>> getPagedBroadcast(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(broadcastService.findAll(page, pageSize));
    }

    @GetMapping("/name")
    public ResponseEntity<List<Broadcast>> getPagedBroadcastByName(@RequestParam(name = "name") String name,
                                                                   @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(broadcastService.findAllByName(name, page, pageSize));
    }
}

