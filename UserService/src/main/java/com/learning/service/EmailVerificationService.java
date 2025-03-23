package com.learning.service;

import com.learning.entities.User;

public interface EmailVerificationService {
    void scheduleEmailVerification(User user);
}
