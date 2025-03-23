package com.learning.repositories;

import com.learning.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    Map<String,User> users = new HashMap<>();
    @Override
    public boolean save(User user) {
        boolean returnValue = false;
        if(!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            returnValue = true;
        }
        return returnValue;
    }
}
