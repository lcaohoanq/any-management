package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.dtos.CarRentalDTO;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.services.CarRentalService;
import com.lcaohoanq.fucar.services.ICarRentalService;
import com.lcaohoanq.fucar.utils.AlertHandler;
import com.lcaohoanq.fucar.utils.WriteFileHandler;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionReportController {

    private final ICarRentalService carRentalService;
    private final ObservableList<CarRental> tableModel = FXCollections.observableArrayList();
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button generateReportButton;
    @FXML private TableView<CarRental> reportTable;
    @FXML private TableColumn<CarRental, Long> rentalIdColumn;
    @FXML private TableColumn<CarRental, String> customerNameColumn;
    @FXML private TableColumn<CarRental, String> carNameColumn;
    @FXML private TableColumn<CarRental, LocalDate> pickupDateColumn;
    @FXML private TableColumn<CarRental, LocalDate> returnDateColumn;
    @FXML private TableColumn<CarRental, BigDecimal> rentPriceColumn;

    public TransactionReportController() {
        this.carRentalService = new CarRentalService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @FXML
    public void initialize() {
        // Set up table columns
        setupTableColumns();
        loadCarRentalTable();

        reportTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,  newSelection) -> {
            if (newSelection != null) {
//                showReportData(newSelection);
            }
        });

        // Set button action
        generateReportButton.setOnAction(event -> generateReport());
    }

    private void setupTableColumns() {
        rentalIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(cellData ->
                                           new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        carNameColumn.setCellValueFactory(cellData ->
                                      new SimpleStringProperty(cellData.getValue().getCar().getCarName()));
        pickupDateColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        rentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
    }

    private void loadCarRentalTable() {
        List<CarRental> rentals = carRentalService.findAll();
        tableModel.clear();
        tableModel.addAll(rentals);
        reportTable.setItems(tableModel);
    }

    private void generateReport() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if(startDate == null || endDate == null || startDate.isAfter(endDate) || startDate.isEqual(endDate) || endDate.isBefore(startDate)){
            AlertHandler.showAlert("Invalid Date Selection", "Please select both start and end dates.");
            return;
        }

        List<CarRental> filteredList = tableModel.stream()
            .filter(rental -> rental.getPickupDate().isAfter(startDate) && rental.getReturnDate().isBefore(endDate))
            .toList();

        if(filteredList.isEmpty()){
            AlertHandler.showAlert("No data found", "No data found for the selected date range.");
            System.out.println("No data found for the selected date range.");
            return;
        }

        List<CarRentalDTO> filteredDTOs = filteredList.stream()
            .map(carRental -> new CarRentalDTO(
                carRental.getId(),
                carRental.getCustomer().getCustomerName(),
                carRental.getCar().getCarName(),
                carRental.getPickupDate(),
                carRental.getReturnDate(),
                carRental.getRentPrice()))
            .toList();


        WriteFileHandler.writeJsonFile(filteredDTOs, ResourcePaths.TRANSACTION_REPORT_JSON);
    }
}
