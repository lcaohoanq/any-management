package com.lcaohoanq.fucar.layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable, Navigable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Set tours_home_page as the default content when the app starts
            setContent("rental", contentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateMyProfile() throws IOException {
        navigateMyProfile(contentArea);
    }

    // Handler for navigating to the Tours page
    @FXML
    private void navigateRental() throws IOException {
        navigateRental(contentArea);
    }

    // Handler for navigating to the Bookings page
    @FXML
    private void navigateMyPurchase() throws IOException {
        navigateMyPurchase(contentArea);
    }

    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }
}