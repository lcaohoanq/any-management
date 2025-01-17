package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.CarProducer;
import com.lcaohoanq.fucar.models.Customer;
import java.util.List;

public interface ICarProducerDAO{
    void save(CarProducer carProducer);
    List<CarProducer> findAll();
    void delete(Integer id);
    void update(CarProducer carProducer);
    CarProducer findById(Integer id);
}