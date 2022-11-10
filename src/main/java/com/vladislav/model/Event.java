package com.vladislav.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;
    @Column(name = "event_name")
    @Expose
    private String eventName;
    @Column(name = "date_time", updatable = false)
    @CreationTimestamp
    @Expose
    private LocalDateTime localDateTime;
    @ManyToOne
    @JoinColumn(name = "file_id")
    @Expose
    private File file;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Expose
    private User user;

    public Event() {
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
