package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.models.Car;
import java.util.List;
import java.util.Optional;

public interface ICarRepository {

    void save(Car car);

    List<Car> findAll();

    void delete(Integer id);

    void update(Car car);

    Car findById(Integer id);

    List<Car> findAllWithCarProducers();

    List<String> getAllCarNames();

    Optional<Car> findByCarName(String name);

}
