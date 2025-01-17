package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.daos.CarProducerDAO;
import com.lcaohoanq.fucar.daos.ICarProducerDAO;
import com.lcaohoanq.fucar.models.CarProducer;
import java.util.List;

public class CarProducerRepository implements ICarProducerRepository{

    private ICarProducerDAO carProducerDAO;

    public  CarProducerRepository(String jpaName) {
        carProducerDAO = new CarProducerDAO(jpaName);
    }

    @Override
    public void save(CarProducer carProducer) {
        carProducerDAO.save(carProducer);
    }

    @Override
    public List<CarProducer> findAll() {
        return carProducerDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        carProducerDAO.delete(id);
    }

    @Override
    public void update(CarProducer carProducer) {
        carProducerDAO.update(carProducer);
    }

    @Override
    public CarProducer findById(Integer id) {
        return carProducerDAO.findById(id);
    }
}
