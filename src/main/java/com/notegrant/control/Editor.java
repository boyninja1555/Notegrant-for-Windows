package com.notegrant.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.notegrant.App;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Editor {
    @FXML
    private TextArea textEditor;

    @FXML
    public void initialize() {
        javafx.application.Platform.runLater(() -> {
            textEditor.requestFocus();

            Scene scene = textEditor.getScene();

            // New Window Keybind(s)
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),
                    () -> handleNewWindow());
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN),
                    () -> handleNewWindow());

            // Save Keybind(s)
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                    () -> handleSave());

            // Open Keybind(s)
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN),
                    () -> handleOpen());
        });
    }

    @FXML
    private void handleNewWindow() {
        try {
            App newApp = new App();
            newApp.openNewWindow(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {
        setupAppDir();

        File documentsDir = getDocumentsDir();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Notegrant");
        fileChooser.setInitialDirectory(documentsDir);
        fileChooser.setInitialFileName("My Notegrant");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Window window = textEditor.getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(file))) {
                writer.write(textEditor.getText());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleOpen() {
        setupAppDir();

        File documentsDir = getDocumentsDir();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Notegrant");
        fileChooser.setInitialDirectory(documentsDir);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Window window = textEditor.getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                textEditor.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupAppDir() {
        File documentsDir = getDocumentsDir();

        if (!documentsDir.exists())
            documentsDir.mkdir();

        if (!documentsDir.isDirectory()) {
            documentsDir.delete();
            documentsDir.mkdir();
        }
    }

    private File getDocumentsDir() {
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "My Notegrants");
        return documentsDir;
    }
}
