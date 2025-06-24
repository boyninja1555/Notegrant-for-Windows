package com.notegrant.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.notegrant.App;
import com.notegrant.util.ConfigManager;
import com.notegrant.util.ConfigManager.ConfigValueType;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigUI {

    @FXML
    private VBox configContainer;

    private final Map<String, Node> fieldMap = new HashMap<>();
    private Stage mainStage;

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    public void initialize() {
        Properties config = ConfigManager.getConfig();

        for (String key : ConfigManager.getAllKeys()) {
            Label label = new Label(capitalize(key).replaceAll("-", " "));

            ConfigValueType type = ConfigManager.getValueType(key);
            Node inputField = null;

            switch (type) {
                case DROPDOWN:
                    ComboBox<String> combo = new ComboBox<>();
                    combo.getItems().addAll(Arrays.asList(ConfigManager.getDropdownOptions(key)));
                    combo.setValue(config.getProperty(key));
                    inputField = combo;
                    break;

                case BOOLEAN:
                    CheckBox checkbox = new CheckBox();
                    checkbox.setSelected(Boolean.parseBoolean(config.getProperty(key)));
                    inputField = checkbox;
                    break;

                case INTEGER:
                case FLOAT:
                case TEXT:
                default:
                    TextField field = new TextField(config.getProperty(key));
                    inputField = field;
                    break;
            }

            fieldMap.put(key, inputField);
            configContainer.getChildren().addAll(label, inputField);
        }
    }

    @FXML
    private void handleSaveAndClose() {
        Properties config = ConfigManager.getConfig();

        for (Map.Entry<String, Node> entry : fieldMap.entrySet()) {
            String key = entry.getKey();
            Node node = entry.getValue();

            String value = "";

            if (node instanceof ComboBox) {
                value = ((ComboBox<?>) node).getValue().toString();
            } else if (node instanceof CheckBox) {
                value = Boolean.toString(((CheckBox) node).isSelected());
            } else if (node instanceof TextField) {
                value = ((TextField) node).getText();
            }

            config.setProperty(key, value);
        }

        ConfigManager.saveConfig();
        Stage configStage = (Stage) configContainer.getScene().getWindow();
        configStage.close();

        if (mainStage != null) {
            mainStage.close();

            try {
                new App().openNewWindow(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String capitalize(String key) {
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }
}
