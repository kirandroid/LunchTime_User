package com.lunchtime.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Comp {

    private String remoteText;

    public Comp(String i){
        this.remoteText = i;
    }

    public void setRemoteText(String remoteText) {
        this.remoteText = remoteText;
    }

    public String getRemoteText() {
        return remoteText;
    }
}
