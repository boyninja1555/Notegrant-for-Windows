package com.notegrant.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TitleBar {
    @FXML
    private Button closeBtn;

    @FXML
    private Button minBtn;

    @FXML
    private HBox titleBar;

    @FXML
    private Region titleBarSpacer;

    private double offsetX;
    private double offsetY;
    private Stage stage;

    public void init(Stage primaryStage) {
        this.stage = primaryStage;

        titleBar.setOnMousePressed(e -> {
            offsetX = e.getSceneX();
            offsetY = e.getSceneY();
        });
        titleBar.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - offsetX);
            stage.setY(e.getScreenY() - offsetY);
        });

        closeBtn.setOnAction(e -> stage.close());
        minBtn.setOnAction(e -> stage.setIconified(true));
    }
}
