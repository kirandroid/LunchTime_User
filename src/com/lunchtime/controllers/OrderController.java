package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXMasonryPane;
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
import javafx.event.ActionEvent;
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

public class OrderController implements Initializable {

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
    private JFXButton allOrderButton;

    @FXML
    private JFXButton pendingButton;

    @FXML
    private JFXButton processingButton;

    @FXML
    private JFXButton readyButton;

    @FXML
    private JFXButton receivedButton;

    @FXML
    private JFXButton cancelledButton;

    @FXML
    private StackPane orderRootPane;

    String clickedStyle = "-fx-background-color: #db0f4b; -fx-text-fill: #FFF";
    String unclickedStyle = "-fx-background-color: #84DCC6; -fx-text-fill: #000";
    ArrayList<Node> children = new ArrayList<>();

    @FXML
    private JFXMasonryPane orderMasonryPane;


    public void allOrderButtonClicked() {
        allOrderButton.setStyle(clickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("All");
    }

    @FXML
    void cancelledButtonClicked(ActionEvent event) {
        allOrderButton.setStyle(unclickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(clickedStyle);
        loadOrderData("Cancelled");
    }

    @FXML
    void pendingButtonClicked(ActionEvent event) {
        allOrderButton.setStyle(unclickedStyle);
        pendingButton.setStyle(clickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("Pending");
    }

    @FXML
    void processingButtonClicked(ActionEvent event) {
        allOrderButton.setStyle(unclickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(clickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("Processing");
    }

    @FXML
    void readyButtonClicked(ActionEvent event) {
        allOrderButton.setStyle(unclickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(clickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("Ready");
    }

    @FXML
    void receivedButtonClicked(ActionEvent event) {
        allOrderButton.setStyle(unclickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(clickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("Received");
    }

    public void loadOrderData(String requestType) {
        UserOrderRequest userOrderRequest = new UserOrderRequest(LoginController.userId, requestType);
        NetworkManager.getInstance().MyOrder(userOrderRequest, new NetworkResponseListener<ApiBaseResponse<OrderWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<OrderWrapper> orderWrapperApiBaseResponse) {
                Platform.runLater(() -> {
                    List<MyOrder> order = orderWrapperApiBaseResponse.getData().getOrder();
                    children.clear();
                    for (int i = 0; i<order.size(); i++){
                        orderItem(order.get(i), requestType);
                    }
                    orderMasonryPane.getChildren().clear();
                    orderMasonryPane.getChildren().addAll(children);
                });
            }

            @Override
            public void onError() {
                System.out.println("Error");
            }
        });
    }

    public void orderItem(MyOrder order, String buttonType){
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
        ImageView imageView = new ImageView(new Image(order.getPicture(), 230, 209, false, true, true));
        header.getChildren().add(imageView);
        String headerColor = "#db0f4b";
        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color:  " + headerColor + ";");

        VBox.setVgrow(header, Priority.ALWAYS);


        StackPane body = new StackPane();
        body.setPrefHeight(200);
        bodyContent.setPadding(new Insets(20,10,10,10));
        foodName.setStyle("-fx-font: 24 arial;");
        foodQuantity.setStyle("-fx-font: 18 arial;");
        foodQuantity.setText("Quantity : "+order.getQuantity().toString());
        foodPrice.setPadding(new Insets(10,0,0,0));
        foodPrice.setTextFill(Color.web("#85bb65"));
        foodPrice.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
        foodName.setText(order.getFood_name());
        foodPrice.setText("Total : Rs. "+order.getTotal_price().toString());

        status.setPadding(new Insets(10,0,0,0));
        status.setTextFill(Color.web("#DB0F4B"));
        status.setStyle("-fx-font: 16 arial; -fx-font-weight: bold");
        status.setText(order.getStatus());

        bodyContent.getChildren().addAll(foodName, foodQuantity, status, foodPrice);
        body.getChildren().add(bodyContent);
        VBox content = new VBox();
        content.getChildren().addAll(header, body);
        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


        if (order.getStatus().equals("Pending")){
            // create button
            JFXButton button = new JFXButton("");
            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: #db0f4b");
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            button.setOnAction(param -> {
                loadDialog(order.getOrder_id(), buttonType);
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
            animation.play();
            stackPane.getChildren().addAll(content, button);
        }else{
            stackPane.getChildren().addAll(content);

        }

    }



    private void loadDialog(int orderId, String buttonType) {
        VBox vBox = new VBox();
        Label message = new Label();
        message.setText("Are you sure you want to cancel this order?");
        vBox.getChildren().add(message);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Cancel Order?"));
        content.setBody(vBox);
        JFXButton cancelButton = new JFXButton("Cancel Order");
        JFXButton exitButton = new JFXButton("Exit");
        JFXDialog dialog = new JFXDialog(orderRootPane, content, JFXDialog.DialogTransition.CENTER);
        content.setActions(exitButton, cancelButton);
        exitButton.setOnAction(event -> dialog.close());
        cancelButton.setOnAction(event -> {
            NetworkManager.getInstance().CancelOrder(orderId, new NetworkResponseListener<ApiBaseResponse>() {
                @Override
                public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                    dialog.close();
                    if (buttonType.equals("Pending")) {
                        loadOrderData("Pending");
                    } else {
                        if (buttonType.equals("All")) {
                            loadOrderData("All");
                        } else {
                            loadOrderData("Pending");
                        }
                    }
                }

                @Override
                public void onError() {

                }
            });
        });
        dialog.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allOrderButton.setStyle(clickedStyle);
        pendingButton.setStyle(unclickedStyle);
        processingButton.setStyle(unclickedStyle);
        readyButton.setStyle(unclickedStyle);
        receivedButton.setStyle(unclickedStyle);
        cancelledButton.setStyle(unclickedStyle);
        loadOrderData("All");
        allOrderButtonClicked();
    }
}
