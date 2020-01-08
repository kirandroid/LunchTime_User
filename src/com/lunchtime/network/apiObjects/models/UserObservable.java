/**
 * @Author Kiran Pradhananga
 * Observable for the User data to notify observers and setting any changes
 * */

package com.lunchtime.network.apiObjects.models;

import java.util.Observable;

public class UserObservable extends Observable {

    public void UserObservable(){
        setChanged();
        notifyObservers();
    }
}
