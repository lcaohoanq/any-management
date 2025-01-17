package com.lcaohoanq.fucar.layouts;

public interface Switchable {

    default void switchToSignUp(){
        /*
        try {
            // Load the signup.fxml file
            URL url = getClass().getResource(ResourcePaths.SIGNUP_VIEW);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Get the current stage (the window that contains the login button)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Option 1: Replace the current scene with the signup scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Option 2: Open signup.fxml in a new window
            // Stage newStage = new Stage();
            // newStage.setScene(new Scene(root));
            // newStage.show();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }*/



    }

}
