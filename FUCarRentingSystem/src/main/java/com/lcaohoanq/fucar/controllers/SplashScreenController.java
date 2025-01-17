package com.lcaohoanq.fucar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

public class SplashScreenController {

    @FXML
    private ProgressIndicator progressIndicator;

    public void setProgress(double progress) {
        progressIndicator.setProgress(progress);
    }
}
