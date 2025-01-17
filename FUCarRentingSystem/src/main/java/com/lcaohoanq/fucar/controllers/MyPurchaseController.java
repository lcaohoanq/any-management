package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ERentalStatus;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.services.CarRentalService;
import com.lcaohoanq.fucar.services.CarService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.ICarRentalService;
import com.lcaohoanq.fucar.services.ICarService;
import com.lcaohoanq.fucar.services.ICustomerService;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyPurchaseController implements Initializable {

    private final ICustomerService customerService;
    private final ICarService carService;
    private final ICarRentalService carRentalService;
    private ObservableList<CarRental> tableModel = FXCollections.observableArrayList();

    @FXML private TableView<CarRental> tblCarRentals;
    @FXML private TableColumn<CarRental, Integer> id;
    @FXML private TableColumn<CarRental, String> customerId;
    @FXML private TableColumn<CarRental, String> carId;
    @FXML private TableColumn<CarRental, LocalDate> pickupDate;
    @FXML private TableColumn<CarRental, LocalDate> returnDate;
    @FXML private TableColumn<CarRental, BigDecimal> rentPrice;
    @FXML private TableColumn<CarRental, ERentalStatus> status;

    @FXML private Button btnCancel;

    private Customer existingCustomer;

    public MyPurchaseController() {
        this.customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
        this.carService = new CarService(ResourcePaths.HIBERNATE_CONFIG);
        this.carRentalService = new CarRentalService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup initial configurations
        loadCustomer();
        setupTableColumns();
        loadCarRentalTable();

        // Setup event listeners
        setupEventListeners();
    }

    private void setupEventListeners() {
        tblCarRentals.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
//                populateFormWithSelectedRental(newSelection);
            }
        });

        btnCancel.setOnAction(event -> {
            Platform.exit();
        });
    }

    private void loadCustomer() {
        existingCustomer = LoginController.customer;
        if (existingCustomer != null) {
            System.out.println("Customer: " + existingCustomer.getCustomerName());
        }
    }

    private void setupTableColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerId.setCellValueFactory(cellData ->
                                           new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        carId.setCellValueFactory(cellData ->
                                      new SimpleStringProperty(cellData.getValue().getCar().getCarName()));
        pickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        rentPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadCarRentalTable() {
        List<CarRental> rentals = carRentalService.findAllByCustomerId(existingCustomer.getCustomerId());
        tableModel.clear();
        tableModel.addAll(rentals);
        tblCarRentals.setItems(tableModel);
    }
}
