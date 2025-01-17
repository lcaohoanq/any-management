package com.lcaohoanq.fucar.layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class AdminMainController implements Initializable, Navigable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Set tours_home_page as the default content when the app starts
            setContent("car_management", contentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateHome() throws IOException {
        navigateHome(contentArea);
    }

    @FXML
    private void navigateMyProfile() throws IOException {
        navigateMyProfile(contentArea);
    }

    @FXML
    private void navigateCarManagement() throws IOException {
        navigateCarManagement(contentArea);
    }

    @FXML
    private void navigateRentingManagement() throws IOException {
        navigateRentingManagement(contentArea);
    }

    @FXML
    private void navigateTransactionReport() throws IOException {
        navigateTransactionReport(contentArea);
    }

    @FXML
    private void navigateUserManagement() throws IOException {
        navigateUserManagement(contentArea);
    }

    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }

}
