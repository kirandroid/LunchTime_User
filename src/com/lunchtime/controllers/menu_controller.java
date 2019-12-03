package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static javafx.animation.Interpolator.EASE_BOTH;

public class menu_controller {
    int quantity = 1;
    @FXML
    private JFXMasonryPane testMasonryPane;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private StackPane menuPane;

    //---------------For making the screen draggable-------------
    double x, y;

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
        stage.setOpacity(0.7f);
    }

    @FXML
    void windowDraggedDone(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setOpacity(1.0f);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    //---------------For making the screen draggable-------------




    @FXML
    void refresh(ActionEvent event) {
        testMasonryPane.getChildren().clear();
        initialize();
    }


    public void initialize() {
        System.out.println("Hello");
        NetworkManager.getInstance().GetMenu(new NetworkResponseListener<ApiBaseResponse<MenuWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<MenuWrapper> menuWrapperApiBaseResponse) {
                System.out.println("Response");
                Platform.runLater(() -> {
                    final List menu = menuWrapperApiBaseResponse.getData().getMenu();
                    ArrayList<Node> children = new ArrayList<>();
                    for (int i = 0; i < menu.size(); i++) {
                        StackPane stackPane = new StackPane();
                        double width = 200;
                        stackPane.setPrefWidth(width);
                        double height = 250;
                        stackPane.setPrefHeight(height);
                        JFXDepthManager.setDepth(stackPane, 1);
                        children.add(stackPane);

                        StackPane header = new StackPane();
                        VBox bodyContent = new VBox();
                        Label foodName = new Label();
                        String headerColor = "#4E6A9C";
                        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" +" -fx-background-color:  "+ headerColor+ "; -fx-background-image: url( "+menuWrapperApiBaseResponse.getData().getMenu().get(i).getPicture()+");");
                        VBox.setVgrow(header, Priority.ALWAYS);


                        StackPane body = new StackPane();
                        body.setPrefHeight(100);
                        foodName.setText(menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_name());
                        bodyContent.getChildren().add(foodName);
                        body.getChildren().add(bodyContent);
                        VBox content = new VBox();
                        content.getChildren().addAll(header, body);
                        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


                        // create button
                        JFXButton button = new JFXButton("");
                        button.setButtonType(JFXButton.ButtonType.RAISED);
                        button.setStyle("-fx-background-radius: 40;-fx-background-color: #16669B");
                        button.setPrefSize(40, 40);
                        button.setRipplerFill(Color.valueOf(headerColor));
                        button.setScaleX(0);
                        button.setScaleY(0);
                        int finalI = i;
                        button.setOnAction(param -> {
                            System.out.println("Clicked"+menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_name());
                            loadDialog(menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_name(), menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_price());
                        });
                        SVGGlyph glyph = new SVGGlyph(-1,
                                "test",
                                "M11 9h2V6h3V4h-3V1h-2v3H8v2h3v3zm-4 9c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zm10 0c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2zm-8.9-5h7.45c.75 0 1.41-.41 1.75-1.03l3.86-7.01L19.42 4l-3.87 7H8.53L4.27 2H1v2h2l3.6 7.59L3.62 17H19v-2H7l1.1-2z",
                                Color.WHITE);
                        glyph.setSize(20, 20);
                        button.setGraphic(glyph);
                        button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                            return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
                        }, header.boundsInParentProperty(), button.heightProperty()));
                        StackPane.setMargin(button, new Insets(0, 12, 0, 0));
                        StackPane.setAlignment(button, Pos.TOP_RIGHT);

                        Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                                new KeyValue(button.scaleXProperty(),
                                        1,
                                        EASE_BOTH),
                                new KeyValue(button.scaleYProperty(),
                                        1,
                                        EASE_BOTH)));
                        animation.setDelay(Duration.millis(100 * i + 1000));
                        animation.play();
                        stackPane.getChildren().addAll(content, button);


                    }
                    testMasonryPane.getChildren().addAll(children);
                });
            }

            @Override
            public void onError() {
                System.out.println("Error on menu fetch");
            }
        });

    }

    private void loadDialog(String foodName, Integer foodPrice){
        JFXSlider priceSlider = new JFXSlider();
        priceSlider.setValue(1);
        Label message = new Label();

        priceSlider.setMin(1);
        priceSlider.setMax(100);
        priceSlider.setMinorTickCount(0);
        priceSlider.setMajorTickUnit(1);
        DecimalFormat df = new DecimalFormat("0.##");
        priceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                message.setText("Would you like to order "+ df.format(newValue) + " plate "+ foodName+" for Rs. "+foodPrice);
            }
        });

//        message.textProperty().bind(Bindings.concat("Would you like to order ", priceSlider.valueProperty().asString("%.0f"), " plate ", foodName, " for Rs. ",  foodPrice));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(message, priceSlider);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Order "+foodName));
        content.setBody(vBox);
        JFXButton button = new JFXButton("Okay");
        JFXDialog dialog = new JFXDialog(menuPane, content, JFXDialog.DialogTransition.CENTER);

        button.setOnAction(event -> dialog.close());
        content.setActions(button);

        dialog.show();
    }
}
