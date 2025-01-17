package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.CarProducer;
import java.util.List;

public interface ICarProducerService {
    void save(CarProducer car);
    List<CarProducer> findAll();
    void delete(Integer id);
    void update(CarProducer car);
    CarProducer findById(Integer id);
}
