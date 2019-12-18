package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.OrderRequest;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.animation.Interpolator.EASE_BOTH;

public class menu_controller implements Initializable {
    private boolean quantityFieldIsEmpty = true;
    private boolean quantityFieldIsValid = false;

    login_controller login_controller = new login_controller();

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
    private JFXButton searchButton;

    @FXML
    private JFXButton refreshButton;


    public void loadData() {
        NetworkManager.getInstance().GetMenu(new NetworkResponseListener<ApiBaseResponse<MenuWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<MenuWrapper> menuWrapperApiBaseResponse) {
                System.out.println("menu");
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
                        Label foodPrice = new Label();
                        ImageView imageView = new ImageView(new Image(menuWrapperApiBaseResponse.getData().getMenu().get(i).getPicture(), 230, 209, false, true, true));

                        header.getChildren().add(imageView);
                        String headerColor = "#4E6A9C";
//                        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color:  " + headerColor + "; -fx-background-image: url( " + menuWrapperApiBaseResponse.getData().getMenu().get(i).getPicture() + ");");
                        header.setStyle("-fx-background-size: cover; -fx-background-radius: 5 5 0 0;" + " -fx-background-color:  " + headerColor + ";");

                        VBox.setVgrow(header, Priority.ALWAYS);


                        StackPane body = new StackPane();
                        body.setPrefHeight(100);
                        bodyContent.setPadding(new Insets(20,10,10,10));
                        foodName.setStyle("-fx-font: 24 arial;");
                        foodPrice.setPadding(new Insets(10,0,0,0));
                        foodPrice.setTextFill(Color.web("#85bb65"));
                        foodPrice.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
                        foodName.setText(menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_name());
                        foodPrice.setText("Rs. "+menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_price().toString());
                        bodyContent.getChildren().addAll(foodName, foodPrice);
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
                            System.out.println("Clicked" + menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_name());
                            loadDialog(menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_name(), menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_price(), menuWrapperApiBaseResponse.getData().getMenu().get(finalI).getFood_id());
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

    private void loadDialog(String foodName, Integer foodPrice, Integer foodId) {
        VBox vBox = new VBox();
        Label message = new Label();
        message.setText("Please specify the quantity of the order.");
        JFXTextField quantityField = new JFXTextField();
        quantityField.setLabelFloat(true);
        quantityField.setPromptText("Enter quantity");
        quantityField.setPadding(new Insets(15, 0, 0, 0));
        vBox.getChildren().addAll(message, quantityField);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Order " + foodName));
        content.setBody(vBox);
        JFXButton orderButton = new JFXButton("Place Order");
        JFXButton cancelButton = new JFXButton("Cancel");
        JFXDialog dialog = new JFXDialog(menuPane, content, JFXDialog.DialogTransition.CENTER);
        content.setActions(cancelButton, orderButton);
        cancelButton.setOnAction(event -> dialog.close());
        orderButton.setOnAction(event -> {
            if (quantityFieldIsValid && !quantityFieldIsEmpty){
                dialog.close();
                int quantity = Integer.parseInt(quantityField.getText());
                int totalPrice = quantity * foodPrice;

                JFXDialogLayout errorContent = new JFXDialogLayout();
                if (totalPrice > login_controller.balance) {
                    errorContent.setHeading(new Text("Error"));
                    errorContent.setBody(new Text("Insufficient Coins"));
                    JFXDialog errorDialog = new JFXDialog(menuPane, errorContent, JFXDialog.DialogTransition.CENTER);
                    JFXButton errorCancelButton = new JFXButton("Cancel");
                    errorContent.setActions(errorCancelButton);

                    errorCancelButton.setOnAction(closeEvent -> errorDialog.close());
                    errorDialog.show();
                } else {
                    errorContent.setHeading(new Text("Place Order"));
                    errorContent.setBody(new Text("The total price is Rs. " + totalPrice + " for quantity: " + quantity + ". Place the order?"));
                    JFXDialog confirmDialog = new JFXDialog(menuPane, errorContent, JFXDialog.DialogTransition.CENTER);
                    JFXButton confirmOrderButton = new JFXButton("Place Order");
                    JFXButton confirmCancelButton = new JFXButton("Cancel");
                    errorContent.setActions(confirmCancelButton, confirmOrderButton);

                    confirmCancelButton.setOnAction(closeEvent -> confirmDialog.close());
                    confirmOrderButton.setOnAction(confirmEvent -> {
                        OrderRequest orderRequest = new OrderRequest(login_controller.userId, foodId, quantity, totalPrice);
                        NetworkManager.getInstance().Order(orderRequest, new NetworkResponseListener<ApiBaseResponse>() {
                            @Override
                            public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                                confirmDialog.close();
                                Platform.runLater(() -> {
                                    JFXDialogLayout orderContent = new JFXDialogLayout();
                                    orderContent.setHeading(new Text("Success"));
                                    orderContent.setBody(new Text("Order Placed!"));
                                    JFXDialog orderDialog = new JFXDialog(menuPane, orderContent, JFXDialog.DialogTransition.CENTER);
                                    JFXButton orderCancelButton = new JFXButton("Cancel");
                                    orderContent.setActions(orderCancelButton);

                                    orderCancelButton.setOnAction(closeEvent -> orderDialog.close());
                                    orderDialog.show();
                                });

                            }

                            @Override
                            public void onError() {
                                System.out.println("Some Error");
                            }
                        });
                    });
                    confirmDialog.show();
                }
            }

        });
        dialog.show();


        //-----------------------------------------------------------------------------------------------------------------//
        //--------Validators-----------//

        //Field Required validator for quantity
        RequiredFieldValidator quantityRequiredFieldValidator = new RequiredFieldValidator();
        quantityField.getValidators().add(quantityRequiredFieldValidator);
        quantityRequiredFieldValidator.setMessage("Please a quantity");
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (quantityField.validate()) {
                    System.out.println("Quantity not empty");
                    quantityFieldIsEmpty = false;
                } else {
                    System.out.println("Quantity empty");
                    quantityFieldIsEmpty = true;
                }

            }
        });
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> quantityField.validate());

//Number Validator
        NumberValidator quantityFieldValidator = new NumberValidator();
        quantityField.getValidators().add(quantityFieldValidator);
        quantityFieldValidator.setMessage("Only numbers accepted!");
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (quantityField.validate()) {
                    System.out.println("Quantity valid");
                    quantityFieldIsValid = true;
                } else {
                    System.out.println("Quantity not valid");
                    quantityFieldIsValid = false;
                }
            }
        });
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> quantityField.validate());
        //--------Validators-----------//
        //-----------------------------------------------------------------------------------------------------------------//

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}
