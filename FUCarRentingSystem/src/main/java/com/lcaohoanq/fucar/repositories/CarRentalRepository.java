package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.daos.CarRentalDAO;
import com.lcaohoanq.fucar.daos.ICarRentalDAO;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
import java.time.LocalDate;
import java.util.List;

public class CarRentalRepository implements ICarRentalRepository{

    private ICarRentalDAO carRentalDAO;

    public CarRentalRepository(String jpaName) {
        carRentalDAO = new CarRentalDAO(jpaName);
    }

    @Override
    public void save(CarRental carRental) {
        carRentalDAO.save(carRental);
    }

    @Override
    public CarRental findById(Integer id) {
        return carRentalDAO.findById(id);
    }

    @Override
    public List<CarRental> findRentalsByDateRange(LocalDate startDate, LocalDate endDate) {
        return carRentalDAO.findRentalsByDateRange(startDate, endDate);
    }

    @Override
    public List<CarRental> findAll() {
        return carRentalDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        carRentalDAO.delete(id);
    }

    @Override
    public void update(CarRental carRental) {
        carRentalDAO.update(carRental);
    }

	@Override
	public boolean isCarExist(Car car) {
		return carRentalDAO.isCarExist(car);
	}

    @Override
    public List<CarRental> findAllByCustomerId(Integer customerId) {
        return carRentalDAO.findAllByCustomerId(customerId);
    }
}
