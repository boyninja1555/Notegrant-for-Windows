package com.notegrant.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.notegrant.App;
import com.notegrant.util.ConfigManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
                    () -> handleOpen(Optional.empty()));
            handleOpen(Optional.of(ConfigManager.getConfig().getProperty("opening-document")));

            // Config Keybind(s)
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.COMMA, KeyCombination.CONTROL_DOWN),
                    () -> handleOpenConfig());
            scene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN),
                    () -> handleOpenConfig());
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
    private void handleOpen(Optional<String> optionalPath) {
        setupAppDir();

        File documentsDir = getDocumentsDir();
        File file = null;

        if (optionalPath != null && optionalPath.isPresent()) {
            file = new File(optionalPath.get());
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Notegrant");
            fileChooser.setInitialDirectory(documentsDir);
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            Window window = textEditor.getScene().getWindow();
            file = fileChooser.showOpenDialog(window);
        }

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

    @FXML
    private void handleOpenConfig() {
        try {
            Stage configStage = new Stage();

            // Title Bar
            configStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader titleBarLoader = new FXMLLoader(getClass().getResource("/component/ConfigUI_TitleBar.fxml"));
            Parent titleBar = titleBarLoader.load();

            TitleBar titleBarController = titleBarLoader.getController();
            titleBarController.init(configStage);

            // UI
            FXMLLoader uiLoader = new FXMLLoader(getClass().getResource("/component/ConfigUI.fxml"));
            Parent uiRoot = uiLoader.load();

            ConfigUI uiController = uiLoader.getController();
            uiController.setMainStage((Stage) textEditor.getScene().getWindow());

            // Window
            VBox root = new VBox();
            root.getChildren().addAll(titleBar, uiRoot);

            Scene configScene = new Scene(root, 400, 600);
            configStage.initStyle(StageStyle.UNDECORATED);
            configStage.setScene(configScene);

            App.resetThemes(configStage.getScene());
            App.loadTheme(getClass(), configStage.getScene());

            configStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

        File exampleDocument = new File(documentsDir.getAbsolutePath() + "/Example.txt");
        String[] exampleDocumentLines = new String[] {
                "Hello, user!",
                "",
                "Welcome to Notegrant. To open a file, press the keys  CTRL+O . To save your note to a file, press the keys  CTRL+S . To open a new window/tab, either press  CTRL+T  or  CTRL+N . To change the app's settings without opening the config file, either type  CTRL+ALT+C  or  CTRL+, .",
        };

        if (!exampleDocument.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(exampleDocument))) {
                writer.write(String.join("\n", exampleDocumentLines));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!documentsDir.isFile()) {
            documentsDir.delete();

            try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(exampleDocument))) {
                writer.write(String.join("\n", exampleDocumentLines));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getDocumentsDir() {
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "My Notegrants");
        return documentsDir;
    }
}
