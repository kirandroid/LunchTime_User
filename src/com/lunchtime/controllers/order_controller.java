package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.MyOrder;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.animation.Interpolator.EASE_BOTH;

public class order_controller implements Initializable{


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
    private StackPane orderPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXMasonryPane orderMasonryPane;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton refreshButton;

    @FXML
    void refresh(ActionEvent event) {
        orderMasonryPane.getChildren().clear();
        loadOrderData();
    }

    public void loadOrderData() {
        refreshButton.setGraphic(new ImageView(new Image(new File("src/com/lunchtime/assets/image/refresh.png").toURI().toString(), 20, 20, false, true, true)));
        searchButton.setGraphic(new ImageView(new Image(new File("src/com/lunchtime/assets/image/search.png").toURI().toString(), 20, 20, false, true, true)));

        NetworkManager.getInstance().MyOrder(16, new NetworkResponseListener<ApiBaseResponse<OrderWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<OrderWrapper> orderWrapperApiBaseResponse) {
                System.out.println("order");

                Platform.runLater(() -> {
                    final List<MyOrder> order = orderWrapperApiBaseResponse.getData().getOrder();
                    ArrayList<Node> children = new ArrayList<>();
                    for (int i = 0; i < order.size(); i++) {
                        System.out.print(order.get(i).getFood_name());

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
                        Label foodPrice = new Label();
                        Label foodQuantity = new Label();
                        String headerColor = "#4E6A9C";
                        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color:  " + headerColor + "; -fx-background-image: url( " + order.get(i).getPicture() + ");");
                        VBox.setVgrow(header, Priority.ALWAYS);


                        StackPane body = new StackPane();
                        body.setPrefHeight(100);
                        bodyContent.setPadding(new Insets(20,10,10,10));
                        foodName.setStyle("-fx-font: 24 arial;");
                        foodQuantity.setStyle("-fx-font: 18 arial;");
                        foodQuantity.setText("Quantity : "+order.get(i).getQuantity().toString());
                        foodPrice.setPadding(new Insets(10,0,0,0));
                        foodPrice.setTextFill(Color.web("#85bb65"));
                        foodPrice.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
                        foodName.setText(order.get(i).getFood_name());
                        foodPrice.setText("Total : Rs. "+order.get(i).getTotal_price().toString());
                        bodyContent.getChildren().addAll(foodName, foodQuantity, foodPrice);
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
                            System.out.println("Clicked" + order.get(finalI).getFood_name());
//                            loadDialog(menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_name(), menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_price(), menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_id());
                        });
                        SVGGlyph glyph = new SVGGlyph(-1,
                                "test",
                                "M22 9.24l-7.19-.62L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21 12 17.27 18.18 21l-1.63-7.03L22 9.24zM12 15.4l-3.76 2.27 1-4.28-3.32-2.88 4.38-.38L12 6.1l1.71 4.04 4.38.38-3.32 2.88 1 4.28L12 15.4z",
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
                    orderMasonryPane.getChildren().addAll(children);
                });
            }

            @Override
            public void onError() {
                System.out.println("Error");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::loadOrderData);
    }
}
