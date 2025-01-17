package com.lcaohoanq.fucar.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertHandler {

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(title.toLowerCase().contains("error") ? Alert.AlertType.ERROR
                                    : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}
