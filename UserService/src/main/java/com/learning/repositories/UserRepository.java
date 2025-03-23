package com.learning.repositories;

import com.learning.entities.User;

public interface UserRepository {
    boolean save(User user);
}
