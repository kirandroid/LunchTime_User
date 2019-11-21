package com.lunchtime.apiservices.models;

public class UserWrapper {
    private User user;

    public User getUser() {
        return user;
    }

    public UserWrapper(User user) {
        this.user = user;
    }
}
