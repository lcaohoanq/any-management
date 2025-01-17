package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.enums.ECarStatus;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.repositories.CarRepository;
import com.lcaohoanq.fucar.repositories.ICarRepository;

import java.util.List;
import java.util.Optional;

public class CarService implements ICarService{

    private final ICarRepository carRepository;
    private final CarRentalService carRentalService;

    public CarService(String name){
        carRepository = new CarRepository(name);
        carRentalService = new CarRentalService(name);
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
    	
    	/*
    	(The delete action will delete car information in the case this information 
    	is not belong to any renting transaction. If the car information is already 
    	stored in a renting transaction, just change the status.)
    	*/
    	Car existingCar = findById(id);
    	if(!carRentalService.isCarExist(existingCar)) {
    		carRepository.delete(id);    		
    	} else {
			// cron job task to update the car Status if reach out of rental day
	        existingCar.setStatus(ECarStatus.RENTED);
	        carRepository.update(existingCar);
	    }
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public Car findById(Integer id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> findAllWithCarProducers() {
        return carRepository.findAllWithCarProducers();
    }

    @Override
    public List<String> getAllCarNames() {
        return carRepository.getAllCarNames();
    }

    @Override
    public Optional<Car> findByCarName(String name) {
        return carRepository.findByCarName(name);
    }
}
