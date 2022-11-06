package com.vladislav.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "date_time", updatable = false)
    private LocalDateTime localDateTime;
    @ManyToOne
    @JoinTable(name = "file_id")
    private File file;
    @ManyToOne
    @JoinTable(name = "user_id")
    private User user;

    public Event() {
        localDateTime = LocalDateTime.now();
    }

    public String getName() {
        return eventName;
    }

    public void setName(String eventName) {
        this.eventName = eventName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
