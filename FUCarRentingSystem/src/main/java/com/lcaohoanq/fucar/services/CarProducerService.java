package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.CarProducer;
import com.lcaohoanq.fucar.repositories.CarProducerRepository;
import com.lcaohoanq.fucar.repositories.ICarProducerRepository;
import java.util.List;

public class CarProducerService implements ICarProducerService{

    private final ICarProducerRepository carProducerRepository;

    public CarProducerService(String name){
        carProducerRepository = new CarProducerRepository(name);
    }

    @Override
    public void save(CarProducer car) {
        carProducerRepository.save(car);
    }

    @Override
    public List<CarProducer> findAll() {
        return carProducerRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        carProducerRepository.delete(id);
    }

    @Override
    public void update(CarProducer car) {
        carProducerRepository.update(car);
    }

    @Override
    public CarProducer findById(Integer id) {
        return carProducerRepository.findById(id);
    }
}
