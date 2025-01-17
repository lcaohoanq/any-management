package com.lcaohoanq.fucar.layouts;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.controllers.CarManagement;
import com.lcaohoanq.fucar.controllers.MyProfileController;
import com.lcaohoanq.fucar.controllers.MyPurchaseController;
import com.lcaohoanq.fucar.controllers.RentalController;
import com.lcaohoanq.fucar.controllers.RentingManagementController;
import com.lcaohoanq.fucar.controllers.TransactionReportController;
import com.lcaohoanq.fucar.controllers.UserManagementController;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.IAccountService;
import com.lcaohoanq.fucar.services.ICustomerService;
import com.lcaohoanq.fucar.utils.NavigateUtil;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public interface Navigable {
    // Default method to navigate to the Home page
    default void navigateHome(StackPane contentArea) throws IOException {
        setContent("home", contentArea);
    }

    // Default method to navigate to the Tours page
    default void navigateTour(StackPane contentArea) throws IOException {
        setContent("tour", contentArea);
    }

    // Default method to navigate to the Bookings page
    default void navigateBooking(StackPane contentArea) throws IOException {
        setContent("booking", contentArea);
    }

    // Default method to navigate to the Settings page
    default void navigateSetting(StackPane contentArea) throws IOException {
        setContent("setting", contentArea);
    }

    default void navigateKoi(StackPane contentArea) throws IOException {
        setContent("koi", contentArea);
    }

    default void navigateStaff(StackPane contentArea) throws IOException {
        setContent("staff", contentArea);
    }

    //navigateMyProfile
    default void navigateMyProfile(StackPane contentArea) throws IOException {
        setContent("my_profile", contentArea);
    }

    //navigateFarm
    default void navigateFarm(StackPane contentArea) throws IOException {
        setContent("farm", contentArea);
    }

    default void navigateLogout(StackPane contentArea) throws IOException {
        NavigateUtil.navigateTo(ResourcePaths.LOGIN_VIEW, contentArea, 830, 650, "Hello!");
    }

    default void navigateTourHomePage(StackPane contentArea) throws IOException {
        setContent("tours_home_page", contentArea);
    }

    //Management
    default void navigateTourManagement(StackPane contentArea) throws IOException {
        setContent("tours_management", contentArea);
    }

    default void navigateSignUp(StackPane contentArea) throws IOException {
        setContent("signup", contentArea);
    }

    default void navigateLogin(StackPane contentArea) throws IOException {
        setContent("login", contentArea);
    }

    default void navigateUserManagement(StackPane contentArea) throws IOException{
        setContent("user_management", contentArea);
    }

    default void navigateCarManagement(StackPane contentArea) throws IOException {
        setContent("car_management", contentArea);
    }

    default void navigateRentingManagement(StackPane contentArea) throws IOException {
        setContent("renting_management", contentArea);
    }

    default void navigateTransactionReport(StackPane contentArea) throws IOException {
        setContent("transaction_report", contentArea);
    }

    //navigateMyPurchase
    default void navigateMyPurchase(StackPane contentArea) throws IOException {
        setContent("my_purchase", contentArea);
    }

    //navigateRental
    default void navigateRental(StackPane contentArea) throws IOException {
        setContent("rental", contentArea);
    }

    // Method to load and set the content in the provided contentArea
    default void setContent(String page, StackPane contentArea) throws IOException {

        try{
            String resources = String.format(ResourcePaths.FXML_DIR, page);

            System.out.println("Resources: " + resources);

            FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(
                    getClass().getResource(resources)));


            AccountService accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
            CustomerService customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
            // preload on startup with initialize method

            //my_profile
            if (page.equals("my_profile")) {
                MyProfileController myProfileController = new MyProfileController();
            }

            //user_management
            if(page.equals("user_management")){
                UserManagementController controller = new UserManagementController();
            }

            if(page.equals("car_management")){
                CarManagement controller = new CarManagement();
            }

            if(page.equals("renting_management")){
                RentingManagementController controller = new RentingManagementController();
            }

            if(page.equals("my_purchase")){
                 MyPurchaseController controller = new MyPurchaseController();
            }

            if(page.equals("rental")){
                RentalController controller = new RentalController();
            }

            if(page.equals("transaction_report")){
                 TransactionReportController controller = new TransactionReportController();
            }

            Node pageContent = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(pageContent);
        }catch (Exception e){
            System.out.println("Error navigate, " + e.getMessage());
        }
    }
}
