package com.lcaohoanq.fucar;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarProducer;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.models.Review;
import com.lcaohoanq.fucar.services.AccountService;
import com.lcaohoanq.fucar.services.CarProducerService;
import com.lcaohoanq.fucar.services.CarRentalService;
import com.lcaohoanq.fucar.services.CarService;
import com.lcaohoanq.fucar.services.CustomerService;
import com.lcaohoanq.fucar.services.ReviewService;

public class TestData {

    public static void main(String[] args) {

        AccountService accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        CustomerService customerService = new CustomerService(ResourcePaths.HIBERNATE_CONFIG);
        CarService carService = new CarService(ResourcePaths.HIBERNATE_CONFIG);
        CarProducerService carProducerService = new CarProducerService(ResourcePaths.HIBERNATE_CONFIG);
        CarRentalService carRentalService = new CarRentalService(ResourcePaths.HIBERNATE_CONFIG);
        ReviewService reviewService = new ReviewService(ResourcePaths.HIBERNATE_CONFIG);

        System.out.println("--------------------ACCOUNT-------------------");
        for(Account account: accountService.findAll()){
            System.out.println(account);
        }

        System.out.println("--------------------CUSTOMER-------------------");
        for(Customer customer: customerService.findAll()){
            System.out.println(customer);
        }

        System.out.println("--------------------CAR-------------------");
        for(Car car: carService.findAll()){
            System.out.println(car);
        }

        System.out.println("--------------------CAR PRODUCER-------------------");
        for(CarProducer carProducer: carProducerService.findAll()){
            System.out.println(carProducer);
        }

        System.out.println("--------------------CAR RENTAL-------------------");
        for(CarRental carRental: carRentalService.findAll()){
            System.out.println(carRental);
        }

        System.out.println("--------------------REVIEW-------------------");
        for(Review review: reviewService.findAll()){
            System.out.println(review);
        }

    }

}
