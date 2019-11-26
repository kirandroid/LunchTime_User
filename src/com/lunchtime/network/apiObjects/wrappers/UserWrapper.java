package com.lunchtime.network.apiObjects.wrappers;

import com.lunchtime.network.apiObjects.models.User;

public class UserWrapper {

    private User user;

    public User getUser() {
        return user;
    }

    public UserWrapper(User user) {
        this.user = user;
    }
}
