package com.lunchtime.apiservices.wrappers;

import com.lunchtime.apiservices.models.User;

public class UserWrapper {

    private User user;

    public User getUser() {
        return user;
    }

    public UserWrapper(User user) {
        this.user = user;
    }
}
