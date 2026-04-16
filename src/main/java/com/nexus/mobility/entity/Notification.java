package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainEnums.NotificationLevel level = DomainEnums.NotificationLevel.INFO;

    @Column(nullable = false)
    private boolean unread = true;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public DomainEnums.NotificationLevel getLevel() {
        return level;
    }

    public void setLevel(DomainEnums.NotificationLevel level) {
        this.level = level;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
