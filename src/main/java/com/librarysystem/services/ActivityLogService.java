package com.librarysystem.services;

import com.librarysystem.models.ActivityLog;
import com.librarysystem.models.User;
import com.librarysystem.repositories.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void logActivity(User user, String activityDetails) {
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivity(activityDetails);
        activityLogRepository.save(log);
    }
}
