package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ECarStatus;
import com.lcaohoanq.fucar.enums.ERentalStatus;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RentingManagementController implements Initializable {

    private final ICustomerService customerService;
    private final ICarService carService;
    private final ICarRentalService carRentalService;
    private ObservableList<CarRental> tableModel = FXCollections.observableArrayList();

    @FXML private TextField txtId;
    @FXML private TextField txtCustomerId;
    @FXML private  TextField txtCarId;
    @FXML private DatePicker dpPickupDate;
    @FXML private DatePicker dpReturnDate;
    @FXML private TextField txtRentPrice;
    @FXML private ComboBox<ERentalStatus> cbStatus;

    @FXML private TableView<CarRental> tblCarRentals;
    @FXML private TableColumn<CarRental, Integer> id;
    @FXML private TableColumn<CarRental, Integer> customerId;
    @FXML private TableColumn<CarRental, Integer> carId;
    @FXML private TableColumn<CarRental, LocalDate> pickupDate;
    @FXML private TableColumn<CarRental, LocalDate> returnDate;
    @FXML private TableColumn<CarRental, BigDecimal> rentPrice;
    @FXML private TableColumn<CarRental, ERentalStatus> status;


    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;

    public RentingManagementController(){
        this.customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
        this.carService = new CarService(ResourcePaths.HIBERNATE_CONFIG);
        this.carRentalService = new CarRentalService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load data into ComboBoxes
        loadRentalStatuses();

        // Set up table columns
        setUpTableColumns();

        // Load data into table
        loadCarRentalTable();

        // Set listener for row selection in the table
        tblCarRentals.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadCarRentalIntoForm(newValue);
            }
        });

        btnAdd.setOnAction(event -> {
            System.out.println("Add button clicked");
        });
        btnUpdate.setOnAction(event -> {
            System.out.println("Update button clicked");
        });
        btnCancel.setOnAction(event -> Platform.exit());
    }

    private void loadCarRentalIntoForm(CarRental carRental) {
        txtId.setText(String.valueOf(carRental.getId()));
        txtCustomerId.setText(String.valueOf(carRental.getCustomer().getCustomerId())); // Assuming `Customer` object has `getId()`
        txtCarId.setText(String.valueOf(carRental.getCar().getCarId())); // Assuming `Car` object has `getId()`
        dpPickupDate.setValue(carRental.getPickupDate()); // Convert SQL date to LocalDate
        dpReturnDate.setValue(carRental.getReturnDate()); // Convert SQL date to LocalDate
        txtRentPrice.setText(carRental.getRentPrice().toString());
        cbStatus.setValue(carRental.getStatus());
    }

    private void loadRentalStatuses() {
        cbStatus.setItems(FXCollections.observableArrayList(ERentalStatus.values()));
    }

    private void setUpTableColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        carId.setCellValueFactory(new PropertyValueFactory<>("car")); // Assuming the property is 'car'
        customerId.setCellValueFactory(new PropertyValueFactory<>("customer")); // Assuming the property is 'customer'
        pickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        rentPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadCarRentalTable() {
        List<CarRental> rentals = carRentalService.findAll();
        tableModel.clear(); // Ensure the list is cleared first
        tableModel.addAll(rentals);
        tblCarRentals.setItems(tableModel);
        clearForm();
    }

    private void clearForm() {
        txtId.clear();
        txtCustomerId.clear();
        txtCarId.clear();
        dpPickupDate.setValue(null);
        dpReturnDate.setValue(null);
        txtRentPrice.clear();
        cbStatus.setValue(null);
    }

}
