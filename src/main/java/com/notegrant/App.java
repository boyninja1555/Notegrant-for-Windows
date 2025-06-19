package com.notegrant;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import com.notegrant.control.TitleBar;
import com.notegrant.util.ConfigManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        openNewWindow(primaryStage);
    }

    public void openNewWindow(Stage stage) throws IOException {
        // Title Bar
        stage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader titleBarLoader = new FXMLLoader(getClass().getResource("/component/TitleBar.fxml"));
        Parent titleBar = titleBarLoader.load();

        TitleBar titleBarController = titleBarLoader.getController();
        titleBarController.init(stage);

        // HTML
        FXMLLoader editorLoader = new FXMLLoader(getClass().getResource("/component/Editor.fxml"));
        Parent editor = editorLoader.load();

        // Creates root
        VBox root = new VBox();
        root.getChildren().addAll(titleBar, editor);

        // Creates scene
        Scene scene = new Scene(root, 850, 480);

        // CSS
        loadTheme(scene);

        // Window settings
        stage.setTitle("Notegrant");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    private void loadTheme(Scene scene) {
        String theme = ConfigManager.getConfig().getProperty("theme");
        Boolean themeLoaded = false;

        URL internalThemeCss = getClass().getResource("/css/" + theme + ".css");

        if (internalThemeCss != null) {
            scene.getStylesheets().add(internalThemeCss.toExternalForm());
            themeLoaded = true;
        }

        if (!themeLoaded) {
            Path configDirPath = ConfigManager.getConfigFile().toPath().getParent();
            Path themesDirPath = configDirPath.resolve("themes");
            File themesDir = new File(themesDirPath.toUri());

            if (!themesDir.exists())
                themesDir.mkdir();

            if (!themesDir.isDirectory()) {
                themesDir.delete();
                themesDir.mkdir();
            }

            Path externalThemeCss = themesDirPath.resolve(theme + ".css");

            if (Files.exists(externalThemeCss)) {
                scene.getStylesheets().add(externalThemeCss.toUri().toString());
                themeLoaded = true;
            }
        }

        if (!themeLoaded) {
            System.err.println("Could not load the " + theme + " theme! Using "
                    + ConfigManager.getConfigDefaults().getProperty("theme") + " by default...");
            scene.getStylesheets().add(getClass()
                    .getResource("/css/" + ConfigManager.getConfigDefaults().getProperty("theme") + ".css").toExternalForm());
        }
    }

    public static void main(String[] args) {
        if (ConfigManager.configFileExists()) {
            ConfigManager.loadConfig();
        } else {
            ConfigManager.initConfig();
        }

        launch(args);
    }
}
