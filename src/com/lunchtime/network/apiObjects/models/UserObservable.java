package com.lunchtime.network.apiObjects.models;

import java.util.Observable;

public class UserObservable extends Observable {

    public void UserObservable(){
        setChanged();
        notifyObservers();
    }
}
