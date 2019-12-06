package com.lunchtime.network.observers;

import com.lunchtime.network.apiObjects.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class TestObserver extends Task<ObservableList<User>> {

    public ArrayList<User> user = new ArrayList<>();

    @Override
    public ObservableList<User> call() throws Exception {
        return FXCollections.observableArrayList(user);
    }
}
