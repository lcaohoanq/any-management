package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.IAccountService;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Setter;

@Setter
public class MyProfileController {

    @FXML
    private Label labelTitle;
    @FXML
    private Label identityCardLabel;
    @FXML
    private Label licenceNumberLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label licenceDateLabel;
    @FXML
    private Label birthDayLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileLabel;
    @FXML
    private Label roleLabel;

    @FXML
    private Button editButton;

    private Account existingAccount;

    private final IAccountService accountService;

    public MyProfileController() {
        this.accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @FXML
    public void initialize() {
        // Load a specific account (e.g., based on login or selection from another screen)
        loadAccountProfile(LoginController.customer); // For demo, loading the first account
    }

    public void loadAccountProfile(Customer customer) {
        // Set labels with account data
        labelTitle.setText(String.format("Hello, %s have a nice day!", customer.getAccount().getAccountName()));
        identityCardLabel.setText(customer.getIdentityCard());
        licenceNumberLabel.setText(customer.getLicenceNumber());
        fullNameLabel.setText(customer.getAccount().getAccountName());
        licenceDateLabel.setText(customer.getLicenceDate().toString());
        birthDayLabel.setText(customer.getBirthday().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        emailLabel.setText(customer.getEmail());
        mobileLabel.setText(customer.getMobile());
        roleLabel.setText(customer.getAccount().getRole().name()); // Role is an enum, so get its name
    }

    @FXML
    private void handleEditButtonAction() {
        // Logic for editing account profile
//        System.out.println("Editing account: " + selectedAccount.getUsername());
        // Implement edit logic here (e.g., open edit form or enable fields)
    }

}
