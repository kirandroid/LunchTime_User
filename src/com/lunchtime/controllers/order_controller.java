/**
 * @author Kiran Pradhan
 * This controller class creates a dynamic grid view from the fetched user order table. Only the order whose status is pending are fetched.
 * */

package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.MyOrder;
import com.lunchtime.network.apiObjects.requests.UserOrderRequest;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private JFXMasonryPane orderMasonryPane;

    public void loadOrderData() {
        UserOrderRequest userOrderRequest = new UserOrderRequest(login_controller.userId, "requestType");

        NetworkManager.getInstance().MyOrder(userOrderRequest, new NetworkResponseListener<ApiBaseResponse<OrderWrapper>>() {
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
                        double height = 330;
                        stackPane.setPrefHeight(height);
                        JFXDepthManager.setDepth(stackPane, 1);
                        children.add(stackPane);

                        StackPane header = new StackPane();
                        VBox bodyContent = new VBox();
                        Label foodName = new Label();
                        Label foodPrice = new Label();
                        Label foodQuantity = new Label();
                        Label status = new Label();
                        ImageView imageView = new ImageView(new Image(order.get(i).getPicture(), 230, 209, false, true, true));
                        header.getChildren().add(imageView);
                        String headerColor = "#db0f4b";
                        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color:  " + headerColor + ";");

                        VBox.setVgrow(header, Priority.ALWAYS);


                        StackPane body = new StackPane();
                        body.setPrefHeight(200);
                        bodyContent.setPadding(new Insets(20,10,10,10));
                        foodName.setStyle("-fx-font: 24 arial;");
                        foodQuantity.setStyle("-fx-font: 18 arial;");
                        foodQuantity.setText("Quantity : "+order.get(i).getQuantity().toString());
                        foodPrice.setPadding(new Insets(10,0,0,0));
                        foodPrice.setTextFill(Color.web("#85bb65"));
                        foodPrice.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
                        foodName.setText(order.get(i).getFood_name());
                        foodPrice.setText("Total : Rs. "+order.get(i).getTotal_price().toString());

                        status.setPadding(new Insets(10,0,0,0));
                        status.setTextFill(Color.web("#DB0F4B"));
                        status.setStyle("-fx-font: 16 arial; -fx-font-weight: bold");
                        status.setText(order.get(i).getStatus());

                        bodyContent.getChildren().addAll(foodName, foodQuantity, status, foodPrice);
                        body.getChildren().add(bodyContent);
                        VBox content = new VBox();
                        content.getChildren().addAll(header, body);
                        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


                        // create button
                        JFXButton button = new JFXButton("");
                        button.setButtonType(JFXButton.ButtonType.RAISED);
                        button.setStyle("-fx-background-radius: 40;-fx-background-color: #db0f4b");
                        button.setPrefSize(40, 40);
                        button.setRipplerFill(Color.valueOf(headerColor));
                        button.setScaleX(0);
                        button.setScaleY(0);
                        button.setOnAction(param -> {
                            loadDialog();
                        });
                        SVGGlyph glyph = new SVGGlyph(-1,
                                "CancelIcon",
                                "M7 11v2h10v-2H7zm5-9C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z",
                                Color.WHITE);
                        glyph.setSize(20, 20);
                        button.setGraphic(glyph);
                        button.translateYProperty().bind(Bindings.createDoubleBinding(() -> header.getBoundsInParent().getHeight() - button.getHeight() / 2, header.boundsInParentProperty(), button.heightProperty()));
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

    private void loadDialog() {
        VBox vBox = new VBox();
        Label message = new Label();
        message.setText("Are you sure you want to cancel this order?");
        vBox.getChildren().add(message);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Cancel Order?"));
        content.setBody(vBox);
        JFXButton cancelButton = new JFXButton("Cancel Order");
        JFXButton exitButton = new JFXButton("Exit");
        JFXDialog dialog = new JFXDialog(orderPane, content, JFXDialog.DialogTransition.CENTER);
        content.setActions(exitButton, cancelButton);
        exitButton.setOnAction(event -> dialog.close());
        cancelButton.setOnAction(event -> {
        });
        dialog.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::loadOrderData);
    }
}
