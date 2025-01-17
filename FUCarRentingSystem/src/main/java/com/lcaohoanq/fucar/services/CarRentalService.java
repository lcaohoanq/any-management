package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.repositories.CarRentalRepository;
import com.lcaohoanq.fucar.repositories.ICarRentalRepository;
import java.time.LocalDate;
import java.util.List;

public class CarRentalService implements ICarRentalService{

    private final ICarRentalRepository carRentalRepository;

    public CarRentalService(String name){
        carRentalRepository = new CarRentalRepository(name);
    }

    @Override
    public void save(CarRental car) {
        carRentalRepository.save(car);
    }

    @Override
    public List<CarRental> findAll() {
        return carRentalRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        carRentalRepository.delete(id);
    }

    @Override
    public void update(CarRental car) {
        carRentalRepository.update(car);
    }

    @Override
    public CarRental findById(Integer id) {
        return carRentalRepository.findById(id);
    }

    @Override
    public List<CarRental> findRentalsByDateRange(LocalDate startDate, LocalDate endDate) {
        return carRentalRepository.findRentalsByDateRange(startDate, endDate);
    }

	@Override
	public boolean isCarExist(Car car) {
		return carRentalRepository.isCarExist(car);
	}

    @Override
    public List<CarRental> findAllByCustomerId(Integer customerId) {
        return carRentalRepository.findAllByCustomerId(customerId);
    }
}
