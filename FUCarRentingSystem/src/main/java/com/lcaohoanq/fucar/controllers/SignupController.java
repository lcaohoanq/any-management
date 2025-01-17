package com.lcaohoanq.fucar.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ERole;
import com.lcaohoanq.fucar.exceptions.BadCredentialsException;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.IAccountService;
import com.lcaohoanq.fucar.services.ICustomerService;
import com.lcaohoanq.fucar.utils.AlertHandler;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupController {

    @FXML private TextField txtAccountName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtMobile;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField txtIdentityCard;
    @FXML private TextField txtLicenceNumber;
    @FXML private DatePicker dpLicenceDate;
    @FXML private Button submitButton;
    @FXML private Button loginHere;
    @FXML private ImageView brandingImageView;
    @FXML private ImageView logoImageView;

    private IAccountService accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
    private ICustomerService customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);

    @FXML
    public void initialize() {
        // Add enter key handlers if needed
        submitButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButtonAction(null);
            }
        });
    }

    @FXML
    public void submitButtonAction(ActionEvent event) {
        try {
            // Validate all fields are filled
            validateFields();
            
            // Validate passwords match
            if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {

                AlertHandler.showAlert("Error", "Passwords do not match");

                throw new BadCredentialsException("Passwords do not match");
            }

            // Create Customer object
            Customer customer = Customer.builder()
                .customerName(txtCustomerName.getText())
                .mobile(txtMobile.getText())
                .birthday(dpBirthday.getValue())
                .identityCard(txtIdentityCard.getText())
                .licenceNumber(txtLicenceNumber.getText())
                .licenceDate(dpLicenceDate.getValue())
                .email(txtEmail.getText())
                .password(txtPassword.getText())
                .build();

            // Create Account object
            Account account = Account.builder()
                .accountName(txtAccountName.getText())
                .role(ERole.CUSTOMER) // Default role is CUSTOMER
                .customer(customer)
                .build();

            // Set bidirectional relationship
            customer.setAccount(account);

            // Save customer (this will cascade to account due to @OneToOne(cascade = CascadeType.ALL))
            customerService.save(customer);

            AlertHandler.showAlert("Success", "Account created successfully");
            
            // Redirect to login page
            loginHereAction(event);

        }   catch (BadCredentialsException e) {
            // Handle validation errors specifically
            log.error("Validation error during signup: {}", e.getMessage());
            AlertHandler.showAlert("Error", e.getMessage());
        }   catch (Exception e) {
            // Handle other errors
            log.error("Error during signup: {}", e.getMessage());
            AlertHandler.showAlert("Error", e.getMessage());
        }
    }

    private void validateFields() throws BadCredentialsException {
        if (txtAccountName.getText().isBlank()) throw new BadCredentialsException("Account name is required");
        if (txtEmail.getText().isBlank()) throw new BadCredentialsException("Email is required");
        if (txtPassword.getText().isBlank()) throw new BadCredentialsException("Password is required");
        if (txtConfirmPassword.getText().isBlank()) throw new BadCredentialsException("Confirm password is required");
        if (txtCustomerName.getText().isBlank()) throw new BadCredentialsException("Customer name is required");
        if (txtMobile.getText().isBlank()) throw new BadCredentialsException("Mobile number is required");
        if (dpBirthday.getValue() == null) throw new BadCredentialsException("Birthday is required");
        if (txtIdentityCard.getText().isBlank()) throw new BadCredentialsException("Identity card number is required");
        if (txtLicenceNumber.getText().isBlank()) throw new BadCredentialsException("License number is required");
        if (dpLicenceDate.getValue() == null) throw new BadCredentialsException("License date is required");
    }

    @FXML
    public void loginHereAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ResourcePaths.LOGIN_VIEW));
            loader.setController(new LoginController());
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            log.error("Error navigating to login: {}", e.getMessage());
            AlertHandler.showAlert("Error", "Could not navigate to login page");
        }
    }
}