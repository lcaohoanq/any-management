package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ERentalStatus;
import com.lcaohoanq.fucar.models.Car;
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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RentalController implements Initializable {

    private final ICustomerService customerService;
    private final ICarService carService;
    private final ICarRentalService carRentalService;
    private ObservableList<CarRental> tableModel = FXCollections.observableArrayList();

    @FXML private TextField txtCustomerName;
    @FXML private ComboBox<String> cbCar;
    @FXML private DatePicker dpPickupDate;
    @FXML private DatePicker dpReturnDate;
    @FXML private TextField txtRentPrice;
    @FXML private TextField txtTotalCost;
    @FXML private ComboBox<ERentalStatus> cbStatus;

    @FXML private TableView<CarRental> tblCarRentals;
    @FXML private TableColumn<CarRental, Integer> id;
    @FXML private TableColumn<CarRental, String> customerId;
    @FXML private TableColumn<CarRental, String> carId;
    @FXML private TableColumn<CarRental, LocalDate> pickupDate;
    @FXML private TableColumn<CarRental, LocalDate> returnDate;
    @FXML private TableColumn<CarRental, BigDecimal> rentPrice;
    @FXML private TableColumn<CarRental, ERentalStatus> status;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Label lblMessage;

    private Customer existingCustomer;
    private CarRental selectedRental;

    public RentalController() {
        this.customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
        this.carService = new CarService(ResourcePaths.HIBERNATE_CONFIG);
        this.carRentalService = new CarRentalService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup initial configurations
        setupValidations();
        loadRentalStatuses();
        loadCustomer();
        loadCars();
        setupTableColumns();
        loadCarRentalTable();

        // Setup event listeners
        setupEventListeners();
    }

    private void setupValidations() {
        // Restrict date selections
        dpPickupDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        dpReturnDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate pickupDate = dpPickupDate.getValue();
                setDisable(empty || (pickupDate != null && date.isBefore(pickupDate)));
            }
        });
    }

    private void setupEventListeners() {
        // Date change listeners for total cost calculation
        dpReturnDate.valueProperty().addListener((obs, oldDate, newDate) -> calculateTotalCost());
        dpPickupDate.valueProperty().addListener((obs, oldDate, newDate) -> calculateTotalCost());

        // Car selection listener to update rent price
        cbCar.setOnAction(event -> updateRentPriceForSelectedCar());

        // Table row selection listener
        tblCarRentals.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormWithSelectedRental(newSelection);
            }
        });

        // Button event handlers
        btnAdd.setOnAction(event -> addCarRental());
        btnUpdate.setOnAction(event -> updateCarRental());
        btnCancel.setOnAction(event -> clearForm());
    }

    private void loadCustomer() {
        existingCustomer = LoginController.customer;
        if (existingCustomer != null) {
            txtCustomerName.setText(existingCustomer.getCustomerName());
            txtCustomerName.setEditable(false);
        }
    }

    private void loadCars() {
        List<String> cars = carService.getAllCarNames();
        cbCar.setItems(FXCollections.observableArrayList(cars));
    }

    private void updateRentPriceForSelectedCar() {
        String selectedCarName = cbCar.getValue();
        if (selectedCarName != null) {
            Optional<Car> car = carService.findByCarName(selectedCarName);
            car.ifPresent(c -> txtRentPrice.setText(c.getRentPrice().toString()));
        }
    }

    private void calculateTotalCost() {
        LocalDate pickup = dpPickupDate.getValue();
        LocalDate dropoff = dpReturnDate.getValue();

        if (pickup == null || dropoff == null || pickup.isAfter(dropoff)) {
            txtTotalCost.clear();
            return;
        }

        try {
            BigDecimal dailyRate = new BigDecimal(txtRentPrice.getText());
            long days = java.time.temporal.ChronoUnit.DAYS.between(pickup, dropoff);
            BigDecimal totalCost = dailyRate.multiply(BigDecimal.valueOf(days + 1)); // Include pickup day
            txtTotalCost.setText(totalCost.toString());
        } catch (NumberFormatException e) {
            txtTotalCost.clear();
        }
    }

    private void addCarRental() {
        if (!validateForm()) return;

        try {
            Car selectedCar = carService.findByCarName(cbCar.getValue()).orElseThrow();

            CarRental newRental = new CarRental();
            newRental.setCustomer(existingCustomer);
            newRental.setCar(selectedCar);
            newRental.setPickupDate(dpPickupDate.getValue());
            newRental.setReturnDate(dpReturnDate.getValue());
            newRental.setRentPrice(new BigDecimal(txtRentPrice.getText()));
//            newRental.setTotalCost(new BigDecimal(txtTotalCost.getText()));
            newRental.setStatus(ERentalStatus.PENDING);

            carRentalService.save(newRental);
            loadCarRentalTable();
            showMessage("Rental added successfully!", Color.GREEN);
        } catch (Exception e) {
            showMessage("Error adding rental: " + e.getMessage(), Color.RED);
        }
    }

    private void updateCarRental() {
        if (selectedRental == null) {
            showMessage("Please select a rental to update", Color.RED);
            return;
        }

        if (!validateForm()) return;

        try {
            Car selectedCar = carService.findByCarName(cbCar.getValue()).orElseThrow();

            selectedRental.setCar(selectedCar);
            selectedRental.setPickupDate(dpPickupDate.getValue());
            selectedRental.setReturnDate(dpReturnDate.getValue());
            selectedRental.setRentPrice(new BigDecimal(txtRentPrice.getText()));
//            selectedRental.setTotalCost(new BigDecimal(txtTotalCost.getText()));
            selectedRental.setStatus(cbStatus.getValue());

            carRentalService.update(selectedRental);
            loadCarRentalTable();
            showMessage("Rental updated successfully!", Color.GREEN);
        } catch (Exception e) {
            showMessage("Error updating rental: " + e.getMessage(), Color.RED);
        }
    }

    private boolean validateForm() {
        if (cbCar.getValue() == null) {
            showMessage("Please select a car", Color.RED);
            return false;
        }
        if (dpPickupDate.getValue() == null) {
            showMessage("Please select pickup date", Color.RED);
            return false;
        }
        if (dpReturnDate.getValue() == null) {
            showMessage("Please select return date", Color.RED);
            return false;
        }
        if (cbStatus.getValue() == null) {
            showMessage("Please select rental status", Color.RED);
            return false;
        }
        return true;
    }

    private void populateFormWithSelectedRental(CarRental rental) {
        selectedRental = rental;
        txtCustomerName.setText(rental.getCustomer().getCustomerName());
        cbCar.setValue(rental.getCar().getCarName());
        dpPickupDate.setValue(rental.getPickupDate());
        dpReturnDate.setValue(rental.getReturnDate());
        txtRentPrice.setText(rental.getRentPrice().toString());
//        txtTotalCost.setText(rental.getTotalCost().toString());
        cbStatus.setValue(rental.getStatus());
    }

    private void loadRentalStatuses() {
        //default status is PENDING
        cbStatus.setItems(FXCollections.observableArrayList(ERentalStatus.PENDING));
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

    private void clearForm() {
        cbCar.setValue(null);
        dpPickupDate.setValue(null);
        dpReturnDate.setValue(null);
        txtRentPrice.clear();
        txtTotalCost.clear();
        cbStatus.setValue(null);
        selectedRental = null;
        lblMessage.setText("");
    }

    private void showMessage(String message, Color color) {
        lblMessage.setText(message);
        lblMessage.setTextFill(color);
    }
}