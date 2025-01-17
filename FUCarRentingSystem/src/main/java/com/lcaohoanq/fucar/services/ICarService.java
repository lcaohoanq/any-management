package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Car;
import java.util.List;
import java.util.Optional;

public interface ICarService {
    void save(Car car);

    List<Car> findAll();

    void delete(Integer id);

    void update(Car car);

    Car findById(Integer id);

    List<Car> findAllWithCarProducers();
    List<String> getAllCarNames();
    Optional<Car> findByCarName(String name);
}
