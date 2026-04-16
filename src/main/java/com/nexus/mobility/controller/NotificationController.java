package com.nexus.mobility.controller;

import com.nexus.mobility.entity.Notification;
import com.nexus.mobility.service.NotificationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/notifications", "/api/v1/notifications"})
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> list(@RequestParam UUID userId, @RequestParam(required = false) Boolean unread) {
        return notificationService.list(userId, unread);
    }

    @PatchMapping("/{id}/read")
    public Notification markRead(@PathVariable UUID id) {
        return notificationService.markRead(id);
    }

    @PatchMapping("/read-all")
    public List<Notification> readAll(@RequestParam UUID userId) {
        return notificationService.readAll(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        notificationService.delete(id);
    }
}
