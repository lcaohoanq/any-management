package com.lcaohoanq.fucar.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.controllers.LoginController;
import com.lcaohoanq.fucar.services.AccountService;

public class NavigateUtil {


    public static void navigateTo(String fxmlResource,
                                  Node sourceNode,
                                  Integer width,
                                  Integer height,
                                  String title) {
        try {
            // Load the new layout
            FXMLLoader loader = new FXMLLoader(NavigateUtil.class.getResource(fxmlResource));

            //Manually set the controller for the login page
            if(fxmlResource.equals(ResourcePaths.LOGIN_VIEW)){
                loader.setController(new LoginController());
            }

            Parent root = loader.load();

            // Get the current stage (window)
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();
            currentStage.close();

            // Set up the new stage
            Stage newStage = new Stage();

            // Set default values for height and width if not provided
            double sceneHeight = (height != null) ? height : 800;  // Default width
            double sceneWidth = (width != null) ? width : 600; // Default height

            Scene scene = new Scene(root, sceneWidth, sceneHeight);
            newStage.setScene(scene);

            // Set the title, default to "Application Title" if not specified
            newStage.setTitle((title != null) ? title : "Application Title");

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            AlertHandler.showAlert("Error", "Failed to load the specified layout.");
        }
    }

}
