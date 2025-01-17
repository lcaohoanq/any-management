package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.ECarStatus;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarProducer;
import com.lcaohoanq.fucar.services.CarProducerService;
import com.lcaohoanq.fucar.services.CarService;
import com.lcaohoanq.fucar.services.ICarProducerService;
import com.lcaohoanq.fucar.services.ICarService;
import com.lcaohoanq.fucar.utils.AlertHandler;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CarManagement implements Initializable {

    private ICarService carService;
    private ICarProducerService carProducerService;
    private ObservableList<Car> tableModel;

    @FXML private TextField txtCarID;
    @FXML private TextField txtCarName;
    @FXML private TextField txtModelYear;
    @FXML private TextField txtColor;
    @FXML private TextField txtCapacity;
    @FXML private TextField txtDescription;
    @FXML private DatePicker dpImportDate;
    @FXML private TextField txtRentPrice;
    @FXML private ComboBox<ECarStatus> cbStatus;
    @FXML private ComboBox<CarProducer> cbProducer;
    

    @FXML private TableView<Car> tblCars;
    @FXML private TableColumn<Car, Integer> carId;
    @FXML private TableColumn<Car, String> carName;
    @FXML private TableColumn<Car, Integer> carModelYear;
    @FXML private TableColumn<Car, String> color;
    @FXML private TableColumn<Car, Integer> capacity;
    @FXML private TableColumn<Car, LocalDate> importDate;
    @FXML private TableColumn<Car, String> description;
    @FXML private TableColumn<Car, ECarStatus> status;
    @FXML private TableColumn<Car, BigDecimal> rentPrice;
    @FXML private TableColumn<Car, String> producer;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnCancel;

    public CarManagement() {
        // Initialize services
        this.carService = new CarService(ResourcePaths.HIBERNATE_CONFIG);
        this.carProducerService = new CarProducerService(ResourcePaths.HIBERNATE_CONFIG);
        // Initialize the table data
        this.tableModel = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns
        initializeTableColumns();

        // Load initial data into the table
        refreshDataTable();

        // Populate producer combo box
        List<CarProducer> producers = carProducerService.findAll();
        cbProducer.setItems(FXCollections.observableArrayList(producers));
        
        cbStatus.setItems(FXCollections.observableArrayList(ECarStatus.values()));

        // Handle table row selection
        tblCars.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showCarData(newValue);
                txtCarID.setManaged(false); //Not managed, not ocuppy space
                txtCarID.setVisible(false); // Invisible of the car ID
            }
        });
        
        dpImportDate.setValue(LocalDate.now()); //ensure import day are calculate from now
    }

    private void initializeTableColumns() {
        // Bind columns with Car properties
        carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
        carModelYear.setCellValueFactory(new PropertyValueFactory<>("carModelYear"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        importDate.setCellValueFactory(new PropertyValueFactory<>("importDate"));
        rentPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        producer.setCellValueFactory(cellData ->
                                         new SimpleStringProperty(cellData.getValue().getProducer().getProducerName())
        );
    }

    @FXML
    public void btnAddOnAction() {
        try {
            Car car = getCarFromInput();
            carService.save(car);
            
            AlertHandler.showAlert(null, "Add new car sucessfully");
            
            refreshDataTable();
        } catch (Exception e) {
            // Handle alert logic here
        }
    }

    @FXML
    public void btnUpdateOnAction() {
        try {
            Integer id = Integer.parseInt(txtCarID.getText().trim());
            Car existingCar = carService.findById(id);
            if (existingCar == null) {
                throw new Exception("Car not found with id: " + id);
            }
            updateCarFromInput(existingCar);
            carService.update(existingCar);
            
            refreshDataTable();

            AlertHandler.showAlert(null, "Update sucessfully");
            
        } catch (Exception e) {
            // Handle alert logic here
        }
    }

    @FXML
    public void btnDeleteOnAction() {
        try {
            Integer id = Integer.parseInt(txtCarID.getText().trim());
            if (carService.findById(id) == null) {
                throw new Exception("Car not found with id: " + id);
            }

            // Show confirmation dialog before deletion
            boolean confirmDelete = AlertHandler.showConfirmation("Confirm Deletion", 
                "Are you sure you want to delete the car with ID " + id + "?");

            if (confirmDelete) {
                carService.delete(id);
                refreshDataTable();
                AlertHandler.showAlert("Delete Successfully", null);
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error when deleting car: " + e.getMessage());
        }
    }


    @FXML
    public void btnCancelOnAction() {
        Platform.exit();
    }

    private Car getCarFromInput() throws Exception {
        validateInputs();
        return Car.builder()
            .carName(txtCarName.getText().trim())
            .carModelYear(Integer.parseInt(txtModelYear.getText().trim()))
            .color(txtColor.getText().trim())
            .capacity(Integer.parseInt(txtCapacity.getText().trim()))
            .description(txtDescription.getText().trim())
            .rentPrice(new BigDecimal(txtRentPrice.getText()))
            .importDate(dpImportDate.getValue())
            .status(cbStatus.getValue())
            .producer(cbProducer.getValue())
            .build();
    }

    private void updateCarFromInput(Car car) throws Exception {
        Car updatedCar = getCarFromInput();
        car.setCarName(updatedCar.getCarName());
        car.setCarModelYear(updatedCar.getCarModelYear());
        car.setColor(updatedCar.getColor());
        car.setCapacity(updatedCar.getCapacity());
        car.setDescription(updatedCar.getDescription());
        car.setRentPrice(updatedCar.getRentPrice());
        car.setImportDate(updatedCar.getImportDate());
        car.setStatus(updatedCar.getStatus());
        car.setProducer(updatedCar.getProducer());
    }

    private void showCarData(Car car) {
        txtCarID.setText(String.valueOf(car.getCarId()));
        txtCarName.setText(car.getCarName());
        txtModelYear.setText(String.valueOf(car.getCarModelYear()));
        txtColor.setText(car.getColor());
        txtCapacity.setText(String.valueOf(car.getCapacity()));
        txtRentPrice.setText(String.valueOf(car.getRentPrice()));
        txtDescription.setText(car.getDescription());
        dpImportDate.setValue(car.getImportDate());
        cbStatus.setValue(car.getStatus());
        cbProducer.setValue(car.getProducer());
    }

    private void refreshDataTable() {
        tableModel.setAll(carService.findAllWithCarProducers());
        tblCars.setItems(tableModel);
        clearInputFields();
    }

    private void clearInputFields() {
        txtCarID.clear();
        txtCarName.clear();
        txtModelYear.clear();
        txtColor.clear();
        txtCapacity.clear();
        txtRentPrice.clear();
        txtDescription.clear();
        dpImportDate.setValue(null);
        cbStatus.setValue(null);
        cbProducer.setValue(null);
    }

    private void validateInputs() throws Exception {
        if (txtCarName.getText().trim().isEmpty() ||
            txtModelYear.getText().trim().isEmpty() ||
            txtColor.getText().trim().isEmpty() ||
            txtCapacity.getText().trim().isEmpty() ||
            txtRentPrice.getText().trim().isEmpty() ||
            txtDescription.getText().trim().isEmpty() ||
            dpImportDate.getValue() == null ||
            cbStatus.getValue() == null ||
            cbProducer.getValue() == null) {
            throw new Exception("All fields are required.");
        }
    }
}
