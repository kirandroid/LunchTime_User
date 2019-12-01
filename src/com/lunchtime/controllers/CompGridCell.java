package com.lunchtime.controllers;

import javafx.scene.control.Label;
import org.controlsfx.control.GridCell;

public class CompGridCell extends GridCell<Comp> {
    @Override
    protected void updateItem(Comp item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {


            setText(item.getRemoteText());
            setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0; -fx-padding: 10; -fx-pref-width: 145; -fx-max-width: 145; -fx-max-width: 145; -fx-pref-height: 130; -fx-max-height: 130; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");
        }
    }


}
