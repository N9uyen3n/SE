package com.librarysystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;

@Entity
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @ManyToOne
    private User user;

    private String activity;
    private Date timestamp;

    public ActivityLog() {
    }

    public ActivityLog(int logId, User user, String activity) {
        this.logId = logId;
        this.user = user;
        this.activity = activity;
        this.timestamp = new Date(); // Set current time on creation
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}