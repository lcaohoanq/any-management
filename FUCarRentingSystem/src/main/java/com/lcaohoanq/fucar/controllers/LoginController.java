package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.layouts.Navigable;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.IAccountService;
import com.lcaohoanq.fucar.services.ICustomerService;
import com.lcaohoanq.fucar.utils.AlertHandler;
import com.lcaohoanq.fucar.utils.EnvUtils;
import com.lcaohoanq.fucar.utils.NavigateUtil;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class LoginController implements Navigable {

    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginButton;
    public static String email = "";
    private String password = "";

    @FXML
    protected ImageView logoImageView;
    @FXML
    protected ImageView logoImageView2;
    @FXML
    protected ImageView logoImageView3;
    @FXML
    protected ImageView ggImageView;
    @FXML
    protected ImageView fbImageView;
    @FXML
    protected ImageView xImageView;

    public static Account account;
    public static Customer customer;

    private final IAccountService accountService;
    private final ICustomerService customerService;

    public LoginController() {
        this.accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        this.customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @FXML
    public void initialize() {

        Image logoImage = new Image(
            Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_M2))
                .toExternalForm());
        logoImageView.setImage(logoImage);

        Image logoImage2 = new Image(
            Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_RWB))
                .toExternalForm());
        logoImageView2.setImage(logoImage2);

        Image logoImage3 = new Image(
            Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_FJCRUSER))
                .toExternalForm());
        logoImageView3.setImage(logoImage3);

        Image ggImage = new Image(
            Objects.requireNonNull(
                    getClass().getResource(ResourcePaths.GOOGLE_VIEW))
                .toExternalForm());
        ggImageView.setImage(ggImage);

        Image fbImage = new Image(
            Objects.requireNonNull(
                    getClass().getResource(ResourcePaths.FACEBOOK_VIEW))
                .toExternalForm());
        fbImageView.setImage(fbImage);

        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter key pressed");
                loginButtonAction(null);  // Trigger login action when Enter key is pressed
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter key pressed");
                loginButtonAction(null);  // Trigger login action when Enter key is pressed
            }
        });
    }


    public void loginButtonAction(ActionEvent event) {
        if ((!usernameTextField.getText().isBlank()) && (!passwordField.getText().isBlank())) {
//            loginMessageLabel.setText("You tried to login");
            validateLogin();
        } else {
            AlertHandler.showAlert("Login Failed", "Please enter email and password");
        }
    }

    public void validateLogin() {
        email = usernameTextField.getText();
        password = passwordField.getText();
        login(email, password);
    }

    private void login(String username, String password) {
        account = accountService.login(username, password);
        customer = customerService.findByIdWithAccount(account.getAccountID());

        if (account == null) {
            AlertHandler.showAlert("Login Failed", "Invalid email or password");
        } else {

            String destinationPage = "";

            switch (account.getRole()) {
                case ADMIN -> destinationPage = "AdminMainLayout";
                case CUSTOMER -> destinationPage = "MainLayout";
                default -> destinationPage = "error";
            }

            NavigateUtil.navigateTo(
                String.format(ResourcePaths.FXML_DIR, destinationPage),
                loginButton, 1500,
                600
                , "Car Renting");
        }
    }

    @FXML
    private void loginViaGoogleAction() {
        Platform.runLater(() -> {
            String googleAuthUrl = EnvUtils.get("GOOGLE_AUTH_URL");
            try {
                URI uri = new URI(googleAuthUrl);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop()
                    .isSupported(Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void loginViaFacebookAction() {
        Platform.runLater(() -> {
            AlertHandler.showAlert("Coming Soon", "This feature is coming soon");
        });
    }

    @FXML
    public void signupHereAction(ActionEvent actionEvent) {
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
        }
    }


    public void forgotPasswordAction(ActionEvent actionEvent) {
        String api = "http://localhost:3000/forgot-password";
        Platform.runLater(() -> {
            try {
                // Specify the URL of the website
                URI uri = new URI(api);
                // Open the website in the default browser
                if (Desktop.isDesktopSupported() && Desktop.getDesktop()
                    .isSupported(Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


}