package com.nexus.mobility.service;

import com.nexus.mobility.entity.Notification;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> list(UUID userId, Boolean unread) {
        List<Notification> items = Boolean.TRUE.equals(unread)
                ? notificationRepository.findByUserIdAndUnread(userId, true)
                : notificationRepository.findByUserId(userId);
        return items.stream().sorted(Comparator.comparing(Notification::getCreatedAt).reversed()).toList();
    }

    @Transactional
    public Notification markRead(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
        notification.setUnread(false);
        return notificationRepository.save(notification);
    }

    @Transactional
    public List<Notification> readAll(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        notifications.forEach(notification -> notification.setUnread(false));
        return notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void delete(UUID id) {
        notificationRepository.delete(notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found.")));
    }
}
