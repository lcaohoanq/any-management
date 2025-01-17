package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.models.Customer;
import java.time.LocalDate;
import java.util.List;

public interface ICarRentalDAO {
    void save(CarRental carRental);
    List<CarRental> findAll();
    void delete(Integer id);
    void update(CarRental carRental);
    CarRental findById(Integer id);
    List<CarRental> findRentalsByDateRange(LocalDate date, LocalDate date1);
    boolean isCarExist(Car car);
    CarRental findByCarId(Integer carId);
    List<Customer> findCustomersByCarId(Integer carId);
    List<CarRental> findAllByCustomerId(Integer customerId);
}
