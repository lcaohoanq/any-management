package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ERole;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.IAccountService;
import com.lcaohoanq.fucar.services.ICustomerService;
import com.lcaohoanq.fucar.utils.AlertHandler;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserManagementController implements Initializable {

    private IAccountService accountService;
    private ICustomerService customerService;
    private final ObservableList<Customer> tableModel;

    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtMobile;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private TextField txtIdentityCard;
    @FXML
    private TextField txtLicenceNumber;
    @FXML
    private DatePicker dpLicenceDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    // Account fields
    @FXML
    private TextField txtAccountName;
    @FXML
    private ComboBox<ERole> cbRole;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Customer> tblUsers;
    @FXML
    private TableColumn<Customer, Integer> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> mobile;
    @FXML
    private TableColumn<Customer, LocalDate> birthday;
    @FXML
    private TableColumn<Customer, String> identityCard;
    @FXML
    private TableColumn<Customer, String> licenceNumber;
    @FXML
    private TableColumn<Customer, LocalDate> licenceDate;
    @FXML
    private TableColumn<Customer, String> email;
    @FXML
    private TableColumn<Customer, String> accountName;
    @FXML
    private TableColumn<Customer, ERole> role;

    public UserManagementController() {
        this.accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        this.customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
        // Get customers with their accounts
        tableModel = FXCollections.observableArrayList(customerService.findAllWithAccounts());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        initializeRoleComboBox();
        tblUsers.setItems(tableModel);

        tblUsers.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    showUserData(newValue);
                    txtCustomerId.setEditable(false);
                }
            });
    }

    private void initializeTableColumns() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        identityCard.setCellValueFactory(new PropertyValueFactory<>("identityCard"));
        licenceNumber.setCellValueFactory(new PropertyValueFactory<>("licenceNumber"));
        licenceDate.setCellValueFactory(new PropertyValueFactory<>("licenceDate"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        accountName.setCellValueFactory(cellData ->
                                            new SimpleStringProperty(
                                                cellData.getValue().getAccount().getAccountName()));
        role.setCellValueFactory(cellData ->
                                     new SimpleObjectProperty<>(
                                         cellData.getValue().getAccount().getRole()));
    }

    private void initializeRoleComboBox() {
        cbRole.setItems(FXCollections.observableArrayList(ERole.values()));
    }

    @FXML
    public void btnAddOnAction() {
        try {
            Customer customer = getCustomerFromInput();
            Account account = getAccountFromInput();

            // Set bidirectional relationship
            customer.setAccount(account);
            account.setCustomer(customer);

            customerService.save(customer);
            refreshDataTable();
            AlertHandler.showAlert("Success", "User successfully added.");
        } catch (Exception e) {
            AlertHandler.showAlert("Error", e.getMessage());
        }
    }

    @FXML
    public void btnUpdateOnAction() {
        try {
            Integer id = Integer.parseInt(txtCustomerId.getText().trim());
            Customer existingCustomer = customerService.findById(id);
            if (existingCustomer == null) {
                throw new Exception("User not found with id: " + id);
            }
            updateUserFromInput(existingCustomer);
            customerService.update(existingCustomer);
            refreshDataTable();
            AlertHandler.showAlert("Success", "User updated successfully.");
        } catch (Exception e) {
            AlertHandler.showAlert("Error", e.getMessage());
        }
    }

    @FXML
    public void btnDeleteOnAction() {
        try {
            Integer id = Integer.parseInt(txtCustomerId.getText().trim());
            if (customerService.findById(id) == null) {
                throw new Exception("User not found with id: " + id);
            }

            boolean confirmDelete = AlertHandler.showConfirmation("Confirm Deletion",
                                                                  "Are you sure you want to delete the car with ID "
                                                                      + id + "?");

            if (confirmDelete) {
                customerService.delete(id);
                refreshDataTable();
                AlertHandler.showAlert("Success", "User deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (Exception e) {
            AlertHandler.showAlert("Error", e.getMessage());
        }
    }

    @FXML
    public void btnCancelOnAction() {
        Platform.exit();
    }

    private Customer getCustomerFromInput() throws Exception {
        validateInputs();

        return Customer.builder()
            .customerName(txtCustomerName.getText().trim())
            .mobile(txtMobile.getText().trim())
            .birthday(dpBirthday.getValue())
            .identityCard(txtIdentityCard.getText().trim())
            .licenceNumber(txtLicenceNumber.getText().trim())
            .licenceDate(dpLicenceDate.getValue())
            .email(txtEmail.getText().trim())
            .password(txtPassword.getText().trim())
            .build();
    }

    private Account getAccountFromInput() throws Exception {
        return Account.builder()
            .accountName(txtAccountName.getText().trim())
            .role(cbRole.getValue())
            .build();
    }

    private void updateUserFromInput(Customer customer) throws Exception {
        Customer updatedCustomer = getCustomerFromInput();
        Account updatedAccount = getAccountFromInput();

        // Update customer fields
        customer.setCustomerName(updatedCustomer.getCustomerName());
        customer.setMobile(updatedCustomer.getMobile());
        customer.setBirthday(updatedCustomer.getBirthday());
        customer.setIdentityCard(updatedCustomer.getIdentityCard());
        customer.setLicenceNumber(updatedCustomer.getLicenceNumber());
        customer.setLicenceDate(updatedCustomer.getLicenceDate());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPassword(updatedCustomer.getPassword());

        // Update account fields
        customer.getAccount().setAccountName(updatedAccount.getAccountName());
        customer.getAccount().setRole(updatedAccount.getRole());
    }

    private void showUserData(Customer customer) {
        txtCustomerId.setText(String.valueOf(customer.getCustomerId()));
        txtCustomerName.setText(customer.getCustomerName());
        txtMobile.setText(customer.getMobile());
        dpBirthday.setValue(customer.getBirthday());
        txtIdentityCard.setText(customer.getIdentityCard());
        txtLicenceNumber.setText(customer.getLicenceNumber());
        dpLicenceDate.setValue(customer.getLicenceDate());
        txtEmail.setText(customer.getEmail());
        txtPassword.setText(customer.getPassword());

        // Account data
        Account account = customer.getAccount();
        if (account != null) {
            txtAccountName.setText(account.getAccountName());
            cbRole.setValue(account.getRole());
        }
    }

    private void validateInputs() throws Exception {
        // Add your validation logic here
        if (txtCustomerName.getText().trim().isEmpty() ||
            txtMobile.getText().trim().isEmpty() ||
            dpBirthday.getValue() == null ||
            txtIdentityCard.getText().trim().isEmpty() ||
            txtLicenceNumber.getText().trim().isEmpty() ||
            dpLicenceDate.getValue() == null ||
            txtEmail.getText().trim().isEmpty() ||
            txtPassword.getText().trim().isEmpty() ||
            txtAccountName.getText().trim().isEmpty() ||
            cbRole.getValue() == null) {
            throw new Exception("All fields are required.");
        }

        // Add more specific validation as needed
        if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("Invalid email format.");
        }
    }

    private void refreshDataTable() {
        tableModel.setAll(customerService.findAllWithAccounts());
        clearInputFields();
    }

    private void clearInputFields() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtMobile.clear();
        dpBirthday.setValue(null);
        txtIdentityCard.clear();
        txtLicenceNumber.clear();
        dpLicenceDate.setValue(null);
        txtEmail.clear();
        txtPassword.clear();
        txtAccountName.clear();
        cbRole.setValue(null);
    }
}