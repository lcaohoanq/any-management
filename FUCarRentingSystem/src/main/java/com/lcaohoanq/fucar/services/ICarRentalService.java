package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ICarRentalService {
    void save(CarRental car);
    List<CarRental> findAll();
    void delete(Integer id);
    void update(CarRental car);
    CarRental findById(Integer id);
    List<CarRental> findRentalsByDateRange(LocalDate startDate, LocalDate endDate);
    boolean isCarExist(Car car);
    List<CarRental> findAllByCustomerId(Integer customerId);
}
