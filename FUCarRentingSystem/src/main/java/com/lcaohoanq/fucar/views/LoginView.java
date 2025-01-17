package com.lcaohoanq.fucar.views;

import java.util.Objects;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.controllers.LoginController;
import com.lcaohoanq.fucar.controllers.SplashScreenController;

public class LoginView extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Step 1: Load the splash screen
            FXMLLoader splashLoader = new FXMLLoader(getClass().getResource(ResourcePaths.SPLASH_SCREEN_VIEW));
            Parent splashRoot = splashLoader.load();
            SplashScreenController splashController = splashLoader.getController();

            Scene splashScene = new Scene(splashRoot);
            Stage splashStage = new Stage();
            splashStage.setScene(splashScene);
            splashStage.show();

            // Step 2: Create a background task to simulate loading (or do actual loading)
            Task<Void> loadTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Simulate loading with a loop
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(50); // Simulate work
                        updateProgress(i + 1, 100);
                    }
                    return null;
                }
            };

            // Bind progress to the splash screen progress indicator
            splashController.setProgress(loadTask.getProgress());

            // Step 3: When loading is complete, show the login view
            loadTask.setOnSucceeded(event -> {
                try {
                    // Close the splash screen
                    splashStage.close();

                    // Load the login view
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(ResourcePaths.LOGIN_VIEW));
                    LoginController controller = new LoginController();
                    loader.setController(controller);
                    Parent loginRoot = loader.load();

                    // Create and configure the login scene
                    Scene loginScene = new Scene(loginRoot, 830, 650);
                    primaryStage.setTitle("Login");
                    primaryStage.getIcons().add(new Image(
                        Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_RWB)).toExternalForm()));

                    primaryStage.setScene(loginScene);
                    primaryStage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error initializing Login: " + e.getMessage());
                }
            });

            // Step 4: Run the task in a background thread
            new Thread(loadTask).start();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing Splash Screen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
